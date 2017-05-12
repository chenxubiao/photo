package cn.chenxubiao.user.repository;

import cn.chenxubiao.user.domain.UserHobby;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by chenxb on 17-4-1.
 */
@Repository
@Transactional
public interface UserHobbyRepository extends PagingAndSortingRepository<UserHobby, Long> {

    int countByCategoryIdAndUserId(int categoryId, int userId);

    List<UserHobby> findByUserId(int userId);

    void deleteAllByUserIdAndAndCategoryIdNotIn(int userId, List<Integer> categoryIds);

    List<UserHobby> findDistinctByCategoryIdIn(List<Integer> cateogyId);
}
