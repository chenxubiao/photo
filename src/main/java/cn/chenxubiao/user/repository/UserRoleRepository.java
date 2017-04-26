package cn.chenxubiao.user.repository;

import cn.chenxubiao.user.domain.UserRole;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by chenxb on 17-4-1.
 */
@Repository
@Transactional
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Long> {

    List<UserRole> findUserRolesByUserId(int userId);
}
