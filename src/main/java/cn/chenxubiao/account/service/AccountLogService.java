package cn.chenxubiao.account.service;


import cn.chenxubiao.account.domain.Account;
import cn.chenxubiao.account.domain.AccountLog;

import java.util.List;

/**
 * Created by chenxb on 17-5-13.
 */
public interface AccountLogService {
    void save(AccountLog accountLog);

    AccountLog findByPicAuth(int userId, int type, int projectId, Account account);

    List<AccountLog> findByAccount(Account account);

    void saveAll(List<AccountLog> accountLogList);

}
