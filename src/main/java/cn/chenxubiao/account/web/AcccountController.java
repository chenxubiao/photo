package cn.chenxubiao.account.web;

import cn.chenxubiao.account.domain.Account;
import cn.chenxubiao.account.domain.AccountLog;
import cn.chenxubiao.account.service.AccountLogService;
import cn.chenxubiao.account.service.AccountService;
import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.web.CommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by chenxb on 17-5-22.
 */
@RestController
public class AcccountController extends CommonController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountLogService accountLogService;

    /**
     * 获取用户账户信息
     * @param request
     * @return
     */

    @RequestMapping(value = "/account/info/data", method = RequestMethod.GET)
    public ResponseEntity getAccountInfo(HttpServletRequest request) {

        UserSession userSession = super.getUserSession(request);
        int userId = userSession.getUserId();
        Account account = accountService.findByUserId(userId);
        int balance = account.getTotalMoney();
        List<AccountLog> accountLogs = accountLogService.findByAccount(account);
        return ResponseEntity.success().set(BBSConsts.DATA, accountLogs).set("balance", balance);
    }
}
