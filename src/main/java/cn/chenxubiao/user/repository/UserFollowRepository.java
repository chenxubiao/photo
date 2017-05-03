package cn.chenxubiao.user.repository;

import cn.chenxubiao.user.domain.UserFollow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenxb on 17-4-1.
 */
@Repository
@Transactional
public interface UserFollowRepository extends PagingAndSortingRepository<UserFollow, Long> {

    @Query(value = "select count (a) from UserFollow a where a.startUserId = ?1 and a.endUserId = ?2")
    int countByStartUserIdAndEndUserId(int startUserId, int endUserId);

    int countByStartUserId(int startUserId);

    int countByEndUserId(int endUserId);

    UserFollow findByStartUserIdAndEndUserId(int startUserId, int endUserId);

    Page<UserFollow> findByStartUserId(int startUserId, Pageable pageable);

    Page<UserFollow> findByEndUserId(int endUserId, Pageable pageable);
}
