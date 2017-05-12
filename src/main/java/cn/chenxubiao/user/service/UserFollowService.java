package cn.chenxubiao.user.service;

import cn.chenxubiao.user.domain.UserFollow;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by chenxb on 17-4-1.
 */
public interface UserFollowService {
    /**
     * 取关/关注用户
     *
     * @param startUserId
     * @param endUserId
     */
    boolean disposeFollowUser(int startUserId, int endUserId);

    /**
     * 是否关注
     * @param startUserId
     * @param endUserId
     * @return
     */
    boolean isFollowed(int startUserId, int endUserId);

    int isFollow(int startUserId, int endUserId);


    UserFollow findByStartUserIdAndEndUserId(int startUserId, int endUserId);

    /**
     * 计算用户被多少人关注
     * @param userId
     * @return
     */
    int countFollows(int userId);

    /**
     * 计算用户关注了多少用户
     * @param userId
     * @return
     */
    int countFollowing(int userId);

    List<UserFollow> findFollows(int userId, Pageable pageable);

    List<UserFollow> findFollowing(int userId, Pageable pageable);

    List<UserFollow> findFollowing(int userId);
}
