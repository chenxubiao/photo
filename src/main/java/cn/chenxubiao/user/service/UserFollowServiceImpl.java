package cn.chenxubiao.user.service;

import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.user.domain.UserFollow;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.repository.UserFollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

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
    public boolean disposeFollowUser(int startUserId, int endUserId) {
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
        UserFollow userFollowDB = findByStartUserIdAndEndUserId(startUserId, endUserId);
        if (userFollowDB != null) {
            userFollowRepository.delete(userFollowDB);
            return true;
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
    public int findFollowsCount(int userId) {
        if (userId <= 0) {
            return 0;
        }
        return userFollowRepository.countByStartUserId(userId);
    }

    @Override
    public boolean isFollowed(int startUserId, int endUserId) {
        if (startUserId <= 0 || endUserId <= 0 || startUserId == endUserId) {
            return false;
        }
        return userFollowRepository.countByStartUserIdAndEndUserId(startUserId, endUserId) > 0;
    }

    @Override
    public UserFollow findByStartUserIdAndEndUserId(int startUserId, int endUserId) {
        if (startUserId <= 0 || endUserId <= 0) {
            return null;
        }
        return userFollowRepository.findByStartUserIdAndEndUserId(startUserId, endUserId);
    }

    @Override
    public int findFollowingCount(int userId) {
        if (userId <= 0) {
            return 0;
        }
        return userFollowRepository.countByEndUserId(userId);
    }

    /**
     * 应取 endUserId
     *
     * @param userId
     * @param pageable
     * @return
     */
    @Override
    public List<UserFollow> findFollows(int userId, Pageable pageable) {

        if (userId <= 0) {
            return null;
        }
        Page<UserFollow> userFollowPage = userFollowRepository.findByStartUserId(userId, pageable);
        return userFollowPage.getContent();
    }

    @Override
    public List<UserFollow> findFollowing(int userId, Pageable pageable) {
        if (userId <= 0) {
            return null;
        }
        Page<UserFollow> userFollowPage = userFollowRepository.findByEndUserId(userId, pageable);
        return userFollowPage.getContent();
    }

}
