package cn.chenxubiao.account.service;


import cn.chenxubiao.account.domain.Account;
import cn.chenxubiao.account.domain.AccountLog;
import cn.chenxubiao.account.repository.AccountLogRepository;
import cn.chenxubiao.common.utils.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by chenxb on 17-5-13.
 */
@Service
@Transactional
public class AccountLogServiceImpl implements AccountLogService {
    @Autowired
    private AccountLogRepository accountLogRepository;

    @Override
    public void save(AccountLog accountLog) {
        if (accountLog == null) {
            return;
        }
        accountLogRepository.save(accountLog);
    }

    @Override
    public AccountLog findByPicAuth(int userId, int type, int projectId, Account account) {
        if (userId <= 0 || type <= 0 || account == null) {
            return null;
        }
        return accountLogRepository.findByUserIdAndTypeAndProjectIdAndAccount(userId, type, projectId, account);
    }

    @Override
    public List<AccountLog> findByAccount(Account account) {
        if (account == null) {
            return null;
        }
        return accountLogRepository.findAllByAccountOrderByIdDesc(account);
    }

    @Override
    public void saveAll(List<AccountLog> accountLogList) {
        if (CollectionUtil.isEmpty(accountLogList)) {
            return;
        }
        accountLogRepository.save(accountLogList);
    }
}
