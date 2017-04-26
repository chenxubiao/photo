package cn.chenxubiao.user.repository;

import cn.chenxubiao.user.domain.UserLoginLog;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenxb on 17-4-1.
 */
@Repository
@Transactional
public interface UserLoginLogRepository extends PagingAndSortingRepository<UserLoginLog, Long> {

}
