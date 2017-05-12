package cn.chenxubiao.user.service;

import cn.chenxubiao.user.domain.UserHobby;

import java.util.List;

/**
 * Created by chenxb on 17-4-1.
 */
public interface UserHobbyService {
    boolean like(int categoryId, int userId);

    boolean isExist(int categoryId, int userId);

    void saveAll(List<UserHobby> userHobbyList);

    List<UserHobby> findByUserId(int userId);

    void deleteNotInCategoryIds(int userId, List<Integer> categoryIds);

    List<UserHobby> findInCategoryId(List<Integer> categoryIds);
}
