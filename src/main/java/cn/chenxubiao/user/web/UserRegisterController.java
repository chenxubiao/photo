package cn.chenxubiao.user.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.HashUtil;
import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.GuestBaseController;
import cn.chenxubiao.user.bean.RegisterBean;
import cn.chenxubiao.user.bean.UserInfoBean;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.domain.UserLoginLog;
import cn.chenxubiao.user.domain.UserRole;
import cn.chenxubiao.user.service.UserInfoService;
import cn.chenxubiao.user.service.UserLoginLogService;
import cn.chenxubiao.user.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenxb on 17-5-1.
 */
@RestController
public class UserRegisterController extends GuestBaseController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserLoginLogService userLoginLogService;

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
        if (StringUtil.isContainSpace(userName)) {
            return ResponseEntity.failure(Errors.USER_USERNAME_IS_CHINESE);
        }
        String password = registerBean.getPassword().trim();
        if (password.length() > 32 || password.length() < 6) {
//            return ResponseEntity.failure(Errors.PASSWORD_LENGTH_ERROR);
        }
        String passwdHash = HashUtil.encrypt(password);
        String email = registerBean.getEmail().trim();
        boolean isEmailExist = userInfoService.isEmailExist(email);
        if (isEmailExist) {
            return ResponseEntity.failure(Errors.EMAIL_IS_EXISTS);
        }
        boolean isUserNameExist = userInfoService.isUserNameExist(userName);
        if (isUserNameExist) {
            return ResponseEntity.failure(Errors.USER_USERNAME_IS_EXISTS);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        userInfo.setUserName(userName);
        userInfo.setCellphone("");
        userInfo.setPassword(passwdHash);
        userInfo.setSex(BBSConsts.UserSex.SEX_UNKNOWN);
        userInfo.setStatus(BBSConsts.UserStatus.USER_IS_NORMAL);
        userInfo.setBirthday(new Date(0L));
        userInfo.setCreateTime(new Date());
        userInfo.setModifyTime(userInfo.getCreateTime());
        userInfo.setUserRole(BBSConsts.CRM_NORMAL);
        userInfoService.save(userInfo);
        UserRole userRole = new UserRole();
        userRole.setUserId(userInfo.getId());
        userRole.setCreateTime(new Date());
        userRole.setModifyTime(userRole.getCreateTime());
        userRole.setRoleId(BBSConsts.UserRole.USER_IS_COMMON);
        userRoleService.save(userRole);
        List<UserRole> userRoleList = new ArrayList<>();
        userRoleList.add(userRole);
        userInfo.setUserRoleList(userRoleList);
        UserSession userSession = super.buildUserSession(userInfo);
        super.setUserSession(request, userSession);
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUserId(userInfo.getId());
        userLoginLog.setIp(request.getRemoteAddr());
        userLoginLog.setCreateTime(new Date());
        userLoginLog.setModifyTime(userLoginLog.getCreateTime());
        userLoginLogService.save(userLoginLog);
        UserInfoBean userInfoBean = new UserInfoBean(userInfo);
        return ResponseEntity.success().set(BBSConsts.DATA, userInfoBean);
    }


}
