package cn.chenxubiao.user.service;

import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.user.domain.UserFollow;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.repository.UserFollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by chenxb on 17-4-1.
 */
@Service
public class UserFollowServiceImpl implements UserFollowService {
    @Autowired
    private UserFollowRepository userFollowRepository;
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public boolean followUser(int startUserId, int endUserId) {
        if (startUserId <= 0 || endUserId <= 0 || startUserId == endUserId) {
            return false;
        }
        UserInfo startUserInfo = userInfoService.findById(startUserId);
        UserInfo endUserInfo = userInfoService.findById(endUserId);
        if (startUserInfo == null
                || endUserInfo == null) {
            return false;
        }
        if (startUserInfo.getStatus() != BBSConsts.UserStatus.USER_IS_NORMAL
                || endUserInfo.getStatus() != BBSConsts.UserStatus.USER_IS_NORMAL) {
            return false;
        }
        boolean isFollowed = isFollowed(startUserId, endUserId);
        if (isFollowed) {
            return false;
        }
        UserFollow userFollow = new UserFollow();
        userFollow.setStartUserId(startUserId);
        userFollow.setEndUserId(endUserId);
        userFollow.setCreateTime(new Date());
        userFollow.setModifyTime(userFollow.getCreateTime());
        userFollowRepository.save(userFollow);
        return true;
    }

    @Override
    public boolean isFollowed(int startUserId, int endUserId) {
        if (startUserId <= 0 || endUserId <= 0 || startUserId == endUserId) {
            return false;
        }
        return userFollowRepository.countByStartUserIdAndEndUserId(startUserId, endUserId) > 0;
    }

}
