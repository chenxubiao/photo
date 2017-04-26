package cn.chenxubiao.user.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.HashUtil;
import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.common.utils.TimeUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.BBSMapping;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.BBSBaseController;
import cn.chenxubiao.neo4j.domain.Person;
import cn.chenxubiao.neo4j.repository.PersonRepository;
import cn.chenxubiao.picture.service.PictureAttachmentService;
import cn.chenxubiao.redis.service.RedisService;
import cn.chenxubiao.user.bean.LoginBean;
import cn.chenxubiao.user.bean.RegisterBean;
import cn.chenxubiao.user.bean.UserProfileBean;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.domain.UserRole;
import cn.chenxubiao.user.service.UserFollowService;
import cn.chenxubiao.user.service.UserHobbyService;
import cn.chenxubiao.user.service.UserInfoService;
import cn.chenxubiao.user.service.UserRoleService;
import com.google.code.kaptcha.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

import static cn.chenxubiao.common.utils.consts.BBSConsts.REDIS_STAT_TOTAL_KEY;
import static cn.chenxubiao.common.utils.consts.BBSConsts.REDIS_TIMESTAMP_KEY;

/**
 * Created by chenxb on 17-3-31.
 */
@RestController
public class UserController extends BBSBaseController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private PictureAttachmentService pictureAttachmentService;
    @Autowired
    private UserFollowService userFollowService;
    @Autowired
    private UserHobbyService userHobbyService;

    /**
     * 用户登录接口
     */
    @RequestMapping(value = "user/login/data", method = RequestMethod.POST)
    public ResponseEntity login(HttpSession session, HttpServletResponse response,
                                HttpServletRequest request,
                                @Valid LoginBean loginBean, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.failure(Errors.PARAMETER_ILLEGAL);
        }
        String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        System.out.println("login session：" + String.valueOf(code) + " id = " + session.getId());
        System.out.println("登录收到验证码：" + loginBean.getCode());
        code = StringUtil.isEmpty(code) ? "" : code;
        if (!code.equals(loginBean.getCode())) {
            return ResponseEntity.failure(Errors.KAPTCHA_ERROR);
        }
        String password = HashUtil.encrypt(loginBean.getPassword());
        UserInfo userInfo = null;
        userInfo = userInfoService.loginByEmail(loginBean.getEmail(), password);
        if (userInfo == null) {
            userInfo = userInfoService.loginByCellphone(loginBean.getCellphone(), password);
            if (userInfo == null) {
                userInfo = userInfoService.loginByUserName(loginBean.getUserName(), password);
            }
        }
        if (userInfo == null) {
            return ResponseEntity.failure(Errors.LOGIN_ERROR);
        }
        if (userInfo.getStatus() == BBSConsts.UserStatus.USER_IS_LOCKING ||
                userInfo.getStatus() == BBSConsts.UserStatus.USER_IS_CLOSE) {
            return ResponseEntity.failure(Errors.USER_IS_LOCKING);
        }
        List<UserRole> userRoleList = userRoleService.findListByUserId(userInfo.getId());
        userInfo.setUserRoleList(userRoleList);
        UserSession userSession = buildUserSession(userInfo);
        session.setAttribute(BBSConsts.USER_SESSION_KEY, userSession);
        request.setAttribute(BBSConsts.USER_SESSION_KEY, userSession);
        return ResponseEntity.success().set("data", userSession);
    }


    /**
     * 用户注册接口
     */
    @RequestMapping(value = "user/register/data", method = RequestMethod.POST)
    public ResponseEntity regester(HttpServletRequest request, HttpSession session,
                                   HttpServletResponse response, RegisterBean registerBean) {

        if (registerBean == null
                || StringUtil.isBlank(registerBean.getUserName())
                || StringUtil.isBlank(registerBean.getEmail())
                || StringUtil.isBlank(registerBean.getPassword())
                || StringUtil.isBlank(registerBean.getCode())) {

            return ResponseEntity.failure(Errors.PARAMETER_ILLEGAL);
        }
        String userName = registerBean.getUserName().trim();
        String password = registerBean.getPassword().trim();
        String passwdHash = HashUtil.encrypt(password);
        String email = registerBean.getEmail().trim();
        boolean isEmailExist = userInfoService.isEmailExist(email);
        if (isEmailExist) {
            return ResponseEntity.failure(Errors.EMAIL_IS_EXISTS);
        }
        boolean isUserNameExist = userInfoService.isUserNameExist(userName);
        if (isUserNameExist) {
            return ResponseEntity.failure(Errors.USERNAME_IS_EXIST);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        userInfo.setUserName(userName);
        userInfo.setCellphone("");
        userInfo.setPassword(passwdHash);
        userInfo.setSex(BBSConsts.UserSex.SEX_UNKNOWN);
        userInfo.setStatus(BBSConsts.UserStatus.USER_IS_NORMAL);
        userInfo.setCreateTime(new Date());
        userInfo.setModifyTime(userInfo.getCreateTime());
        userInfo.setUserRole(BBSConsts.CRM_NORMAL);
        userInfoService.save(userInfo);
        UserSession userSession = super.buildUserSession(userInfo);
        session.setAttribute(BBSConsts.USER_SESSION_KEY, userSession);
        request.setAttribute(BBSConsts.USER_SESSION_KEY, userSession);
        return ResponseEntity.success().set("data", userInfo);
    }

    /**
     * 用户信息完善接口
     */
    @RequestMapping(value = "/user/profile/add", method = RequestMethod.POST)
    public ResponseEntity userProfile(HttpSession session, UserProfileBean userProfile) {
        if (userProfile == null
                || !BBSMapping.USER_SEX_MAPPING.keySet().contains(userProfile.getSex())) {

            return ResponseEntity.failure(Errors.PARAMETER_ILLEGAL);
        }
        UserSession userSession = (UserSession) session.getAttribute(BBSConsts.USER_SESSION_KEY);
        UserInfo userInfo = userInfoService.findById(userSession.getUserId());
        boolean isUserInfoModify = false;
        int avatarId = userProfile.getAvatarId();
        if (avatarId > 0) {
            boolean isAvatarIdExist = pictureAttachmentService.isPictureExist(avatarId);
            if (isAvatarIdExist) {
                userInfo.setAvatarId(avatarId);
                isUserInfoModify = true;
            }
        }
        if (userInfo.getSex() != userProfile.getSex()) {
            userInfo.setSex(userProfile.getSex());
            isUserInfoModify = true;
        }
        if (StringUtil.isNotBlank(userProfile.getBirthday())) {
            Date birthday = TimeUtil.parse(userProfile.getBirthday());
            userInfo.setBirthday(birthday);
            isUserInfoModify = true;
        }
        if (isUserInfoModify) {
            userInfo.setModifyTime(new Date());
            userInfoService.save(userInfo);
        }
        String followIds = userProfile.getUserIds();
        if (StringUtil.isNotBlank(followIds)) {
            String[] followIdStrings = followIds.split(",");
            if (followIdStrings != null) {
                for (String followId : followIdStrings) {
                    if (StringUtil.isNotBlank(followId)) {
                        int startUserId = Integer.parseInt(followId.trim());
                        userFollowService.followUser(startUserId, userInfo.getId());
                    }
                }
            }
        }
        String tagIds = userProfile.getTagIds();
        if (StringUtil.isNotBlank(tagIds)) {
            String[] tagIdStrings = followIds.split(",");
            if (tagIdStrings != null) {
                for (String tagId : tagIdStrings) {
                    if (StringUtil.isNotBlank(tagId)) {
                        int startUserId = Integer.parseInt(tagId.trim());
                        userHobbyService.like(startUserId, userInfo.getId());
                    }
                }
            }
        }
        return ResponseEntity.success();
    }

    @RequestMapping(value = "/user/logout/data", method = RequestMethod.GET)
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        session.setAttribute(BBSConsts.USER_SESSION_KEY, null);
        return ResponseEntity.success();
    }

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/redis/get")
    public String getUserSession() {
        String key = REDIS_STAT_TOTAL_KEY;
        String statTotal = redisService.get(key);
        String timeKey = REDIS_TIMESTAMP_KEY;
        String time = redisService.get(timeKey);
        return statTotal + "---------------" + time;
    }

    @RequestMapping(value = "/redis/add")
    public String setUserSession() {

        return null;
    }

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/neo4j/test")
    public Person set(String name) {

//        personRepository.deleteAll();
//        Person person = new Person(name);
//        personRepository.like(person);
//        System.out.println("*****");
        Person person = personRepository.findByName(name);
        return person;
    }
}
