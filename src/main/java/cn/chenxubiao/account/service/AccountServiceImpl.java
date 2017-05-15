package cn.chenxubiao.account.service;

import cn.chenxubiao.account.domain.Account;
import cn.chenxubiao.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenxb on 17-5-13.
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void save(Account account) {
        if (account == null) {
            return;
        }
        int count = accountRepository.countByUserId(account.getUserId());
        if (count > 0) {
            return;
        }
        accountRepository.save(account);
    }

    @Override
    public Account findByUserId(int userId) {
        if (userId <= 0) {
            return null;
        }
        return accountRepository.findByUserId(userId);
    }

}
