package cn.chenxubiao.user.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.HashUtil;
import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.GuestBaseController;
import cn.chenxubiao.user.bean.LoginBean;
import cn.chenxubiao.user.bean.UserInfoBean;
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
import java.util.Date;
import java.util.List;

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

        if (StringUtil.isBlank(loginBean.getUserName())
                || StringUtil.isBlank(loginBean.getPassword())
                || StringUtil.isBlank(loginBean.getCode())) {

            return ResponseEntity.failure(Errors.PARAMETER_ILLEGAL);
        }
        String name = loginBean.getUserName().trim();

        System.out.println("----------------------------------------------------------------");
        System.out.println("login userName = "+loginBean.getUserName());
        System.out.println("login code = "+loginBean.getCode());
        System.out.println("login passwd = "+loginBean.getPassword());
        System.out.println("login email = "+loginBean.getEmail());
        System.out.println("login phone = "+loginBean.getCellphone());
        System.out.println("----------------------------------------------------------------");
        if (result.hasErrors()) {
            return ResponseEntity.failure(Errors.PARAMETER_ILLEGAL);
        }
        String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        System.out.println("login session：" + String.valueOf(code) + " id = " + session.getId());
        code = StringUtil.isEmpty(code) ? "" : code;
        System.out.println("code = " + code + "loginBean.getCode() = " + loginBean.getCode());
        if (!code.equals(loginBean.getCode())) {
            return ResponseEntity.failure(Errors.KAPTCHA_ERROR);
        }
        UserInfo userInfo = null;
        if (StringUtil.isPhoneNumber(name)) {
            userInfo = userInfoService.findByCellphone(name);
            if (userInfo == null) {
                return ResponseEntity.failure(Errors.CELLPHONE_NULL_ERROR);
            }
        } else if (StringUtil.isEmail(name)) {
            userInfo = userInfoService.findByEmail(name);
            if (userInfo == null) {
                return ResponseEntity.failure(Errors.EMAIL_NOT_FOUNT);
            }
        }else {
            userInfo = userInfoService.findByUserName(name);
            if (userInfo == null) {
                return ResponseEntity.failure(Errors.ACCOUNT_NOT_FOUND);
            }
        }

        String passowrd = HashUtil.encrypt(loginBean.getPassword().trim());
        if (!passowrd.equals(userInfo.getPassword())) {
            return ResponseEntity.failure(Errors.PASSWORD_ERROR);
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
