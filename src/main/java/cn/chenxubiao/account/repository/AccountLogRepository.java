package cn.chenxubiao.account.repository;

import cn.chenxubiao.account.domain.Account;
import cn.chenxubiao.account.domain.AccountLog;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenxb on 17-5-13.
 */
@Transactional
@Repository
public interface AccountLogRepository extends PagingAndSortingRepository<AccountLog, Long> {

    AccountLog findByUserIdAndTypeAndProjectIdAndAccount
            (int userId, int type, int projectId, Account account);

}
