package cn.chenxubiao.user.service;

import cn.chenxubiao.common.utils.TimeUtil;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.domain.UserLoginLog;
import cn.chenxubiao.user.repository.UserLoginLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by chenxb on 17-4-1.
 */
@Service
public class UserLoginLogServiceImpl implements UserLoginLogService {
    @Autowired
    private UserLoginLogRepository userLoginLogRepository;

    @Override
    public void save(UserLoginLog userLoginLog) {
        if (userLoginLog == null) {
            return;
        }
        userLoginLogRepository.save(userLoginLog);
    }

    @Override
    public void logout(int userId) {
        if (userId <= 0) {
            return;
        }
        UserLoginLog userLoginLog = userLoginLogRepository.findFirstByUserIdOrderByIdDesc(userId);
        if (userLoginLog != null) {
            userLoginLog.setLogoutTime(new Date());
            userLoginLog.setModifyTime(userLoginLog.getLogoutTime());
            userLoginLogRepository.save(userLoginLog);
        }
    }

    @Override
    public UserLoginLog findLatestLog(int userId) {
        if (userId <= 0) {
            return null;
        }
        return userLoginLogRepository.findFirstByUserIdOrderByIdDesc(userId);
    }

    @Override
    public int countByCreateTimeBetween(int userId, Date startDate, Date endDate) {
        if (userId <= 0 || startDate == null || endDate == null) {
            return 0;
        }

        return 0;
    }

    @Override
    public boolean isLoginYesterday(int userId) {
        if (userId <= 0) {
            return false;
        }
        return countByCreateTimeBetween(userId, TimeUtil.getDayBefore(), new Date()) > 0;
    }

    @Override
    public UserLoginLog findTodayLoginLog(int userId) {
        if (userId <= 0) {
            return null;
        }
        Date todayBegin = TimeUtil.getTodayBegin();
        Date todayEnd = TimeUtil.disposeDate(todayBegin, 1, TimeUtil.DATE_TYPE_AFTER);
        return userLoginLogRepository.findFirstByUserIdAndAndCreateTimeBetweenOrderByIdAsc
                (userId, todayBegin, todayEnd);
    }

    @Override
    public UserLoginLog findByLoginToday(int userId) {
        return findTodayLoginLog(userId);
    }

    @Override
    public UserLoginLog findYesterdayLoginLog(int userId) {
        if (userId <= 0) {
            return null;
        }
        Date todayBegin = TimeUtil.getTodayBegin();
        Date yesterdayBegin = TimeUtil.disposeDate(todayBegin, 1, TimeUtil.DATE_TYPE_BEFORE);
        return userLoginLogRepository.findFirstByUserIdAndAndCreateTimeBetweenOrderByIdAsc(userId, yesterdayBegin, todayBegin);
    }
}
