package cn.chenxubiao.user.service;

import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.domain.UserLoginLog;

import java.util.Date;

/**
 * Created by chenxb on 17-4-1.
 */
public interface UserLoginLogService {

    void save(UserLoginLog userLoginLog);

    void logout(int userId);

    UserLoginLog findLatestLog(int userId);

    int countByCreateTimeBetween(int userId, Date startDate, Date endDate);

    boolean isLoginYesterday(int userId);

    UserLoginLog findTodayLoginLog(int userId);

    UserLoginLog findByLoginToday(int userId);

    UserLoginLog findYesterdayLoginLog(int userId);
}
