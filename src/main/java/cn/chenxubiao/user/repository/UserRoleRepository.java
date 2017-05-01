package cn.chenxubiao.user.repository;

import cn.chenxubiao.user.domain.UserRole;
import org.springframework.data.jpa.repository.Query;
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

    @Query(value = "select count(a) from UserRole a where a.userId = ?1 and a.roleId = ?2")
    int countByUserIdAndRoleId(int userId, int roleId);

}
