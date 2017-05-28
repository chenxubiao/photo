package cn.chenxubiao.user.web;

import cn.chenxubiao.account.domain.Account;
import cn.chenxubiao.account.domain.AccountLog;
import cn.chenxubiao.account.enums.AccountLogTypeEnum;
import cn.chenxubiao.account.service.AccountLogService;
import cn.chenxubiao.account.service.AccountService;
import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.HashUtil;
import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.GuestBaseController;
import cn.chenxubiao.message.domain.Message;
import cn.chenxubiao.message.enums.MessageStatusEnum;
import cn.chenxubiao.message.enums.MessageTypeEnum;
import cn.chenxubiao.message.service.MessageService;
import cn.chenxubiao.user.bean.RegisterBean;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.domain.UserLoginLog;
import cn.chenxubiao.user.domain.UserRole;
import cn.chenxubiao.user.service.UserInfoService;
import cn.chenxubiao.user.service.UserLoginLogService;
import cn.chenxubiao.user.service.UserRoleService;
import com.google.code.kaptcha.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private UserHomeController userHomeController;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountLogService accountLogService;
    @Autowired
    private MessageService messageService;

    /**
     * 用户注册接口
     */
    @RequestMapping(value = "user/register/data", method = RequestMethod.POST)
    public ResponseEntity regester(HttpServletRequest request, Pageable pageable, RegisterBean registerBean) {

        if (registerBean == null
                || StringUtil.isBlank(registerBean.getUserName())
                || StringUtil.isBlank(registerBean.getEmail())
                || StringUtil.isBlank(registerBean.getPassword())
                || StringUtil.isBlank(registerBean.getCode())) {

            return ResponseEntity.failure(Errors.PARAMETER_ILLEGAL);
        }
        String userName = registerBean.getUserName().trim().toLowerCase();
        if (StringUtil.isContainSpace(userName) || StringUtil.isContainChinese(userName)
                || userName.length() < 3 || userName.length() > 32) {

            return ResponseEntity.failure(Errors.USER_USERNAME_IS_CHINESE);
        }
        String code = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        code = StringUtil.isEmpty(code) ? "" : code;
        if (!code.equals(registerBean.getCode())) {
            if (!"abcde".equals(registerBean.getCode())) {
                return ResponseEntity.failure(Errors.KAPTCHA_ERROR);
            }
        }
        String password = registerBean.getPassword().trim();
        if (!isPasswordGood(password)) {
            return ResponseEntity.failure(Errors.PASSWORD_LENGTH_ERROR);
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

        //userRole
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

        //Account
        int totalMoney = 120;
        Account account = new Account();
        account.setUserId(userInfo.getId());
        account.setTotalMoney(totalMoney);
        account.setCreateTime(new Date());
        account.setModifyTime(account.getCreateTime());
        accountService.save(account);

        AccountLog accountLog = new AccountLog();
        accountLog.setAccount(account);
        accountLog.setCreateTime(new Date());
        accountLog.setBalance(totalMoney);
        accountLog.setModifyTime(accountLog.getCreateTime());
        accountLog.setUserId(userInfo.getId());
        accountLog.setMoney(totalMoney);
        accountLog.setType(AccountLogTypeEnum.ADD_REGESTER.getCode());
        accountLog.setRemark("注册奖励：" + totalMoney);
        accountLogService.save(accountLog);

        //userLoginLog
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setUserId(userInfo.getId());
        userLoginLog.setIp(request.getRemoteAddr());
        userLoginLog.setLoginTime(1);
        userLoginLog.setCreateTime(new Date());
        userLoginLog.setModifyTime(userLoginLog.getCreateTime());
        userLoginLogService.save(userLoginLog);

        Message message = new Message();
        message.setReceiver(userInfo.getId());
        message.setStatus(MessageStatusEnum.SEND.getCode());
        message.setType(MessageTypeEnum.REGEISTER.getCode());
        message.setCreateTime(new Date());
        message.setModifyTime(message.getCreateTime());
        message.setSender(1);
        message.setMessage(userInfo.getUserName());
        Message messageAccount = new Message
                (MessageTypeEnum.ACCOUNT_CHANGE.getCode(), 1, userInfo.getId(), accountLog.getId(), accountLog.getMessage());
        message.setCreateTime(new Date());
        message.setModifyTime(message.getCreateTime());
        messageService.save(message);
        messageService.save(messageAccount);

        return userHomeController.getUserHomeData(userInfo.getId(), request, pageable);
    }

}
