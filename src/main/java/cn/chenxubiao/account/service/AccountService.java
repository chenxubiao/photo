package cn.chenxubiao.account.service;

import cn.chenxubiao.account.domain.Account;

/**
 * Created by chenxb on 17-5-13.
 */
public interface AccountService {

    void save(Account account);

    Account findByUserId(int userId);
}
