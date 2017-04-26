package cn.chenxubiao.user.service;

/**
 * Created by chenxb on 17-4-1.
 */
public interface UserHobbyService {
    boolean like(int tagId, int userId);

    boolean isExist(int tagId, int userId);
}
