package cn.chenxubiao.user.service;

import cn.chenxubiao.user.domain.UserTool;

import java.util.List;

/**
 * Created by chenxb on 17-5-11.
 */
public interface UserToolService {
    boolean isExist(int userId, String name, int type);

    void save(UserTool userTool);

    List<UserTool> findByUserId(int userId);

    void deleteNotInNameList(int userId, int type, List<String> saved);

    void deleteAllByType(int userId, int type);

    void saveAll(List<UserTool> userToolList);
}
