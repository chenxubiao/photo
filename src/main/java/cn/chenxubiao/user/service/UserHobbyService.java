package cn.chenxubiao.user.service;

import cn.chenxubiao.user.domain.UserHobby;

import java.util.List;

/**
 * Created by chenxb on 17-4-1.
 */
public interface UserHobbyService {
    boolean like(int tagId, int userId);

    boolean isExist(int tagId, int userId);

    void saveAll(List<UserHobby> userHobbyList);
}
