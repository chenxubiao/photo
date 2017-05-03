package cn.chenxubiao.user.service;

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
        Pageable pageable = new PageRequest(1, 1, Sort.Direction.DESC, "id");
        Page<UserLoginLog> userLoginLogPage = userLoginLogRepository.findByUserId(userId, pageable);
        if (userLoginLogPage.getContent() == null) {
            return;
        }
        UserLoginLog userLoginLog = userLoginLogPage.getContent().get(0);
        userLoginLog.setLogoutTime(new Date());
        userLoginLog.setModifyTime(userLoginLog.getLogoutTime());
        userLoginLogRepository.save(userLoginLog);
    }
}
