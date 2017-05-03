package cn.chenxubiao.user.service;

import cn.chenxubiao.user.domain.UserLoginLog;

/**
 * Created by chenxb on 17-4-1.
 */
public interface UserLoginLogService {

    void save(UserLoginLog userLoginLog);

    void logout(int userId);
}
