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
import cn.chenxubiao.common.web.GuestBaseController;
import cn.chenxubiao.picture.service.AttachmentService;
import cn.chenxubiao.redis.service.RedisService;
import cn.chenxubiao.user.bean.LoginBean;
import cn.chenxubiao.user.bean.RegisterBean;
import cn.chenxubiao.user.bean.UserInfoBean;
import cn.chenxubiao.user.bean.UserProfileBean;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.domain.UserLoginLog;
import cn.chenxubiao.user.domain.UserRole;
import cn.chenxubiao.user.service.*;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cn.chenxubiao.common.utils.consts.BBSConsts.REDIS_STAT_TOTAL_KEY;
import static cn.chenxubiao.common.utils.consts.BBSConsts.REDIS_TIMESTAMP_KEY;

/**
 * Created by chenxb on 17-3-31.
 */
@RestController
public class UserLoginController extends GuestBaseController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserLoginLogService userLoginLogService;

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
        super.setUserSession(request, userSession);
        UserLoginLog userLoginLog = new UserLoginLog();
        String ip = request.getHeader("X-Real-IP") == null ? "" : request.getHeader("X-Real-IP");
        userLoginLog.setIp(ip);
        userLoginLog.setUserId(userInfo.getId());
        userLoginLog.setCreateTime(new Date());
        userLoginLog.setModifyTime(userLoginLog.getCreateTime());
        userLoginLogService.save(userLoginLog);
        UserInfoBean userInfoBean = new UserInfoBean(userInfo);
        try {
            response.sendRedirect("/user/home/data");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
//        return ResponseEntity.success().set(BBSConsts.DATA, userInfoBean);
    }
}
