package cn.chenxubiao.user.repository;

import cn.chenxubiao.user.domain.UserTool;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by chenxb on 17-5-11.
 */
@Repository
public interface UserToolRepository extends PagingAndSortingRepository<UserTool, Long> {
    int countByUserIdAndNameAndType(int userId, String name, int type);

    List<UserTool> findByUserId(int userId);

    void deleteAllByUserIdAndType(int userId, int type);

    UserTool findByUserIdAndTypeAndName(int userId, int type, String name);

    void deleteAllByUserIdAndTypeAndNameNotIn(int userId, int type, List<String> name);
}
