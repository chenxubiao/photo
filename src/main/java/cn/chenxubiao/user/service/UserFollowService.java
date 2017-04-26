package cn.chenxubiao.user.service;

/**
 * Created by chenxb on 17-4-1.
 */
public interface UserFollowService {
    /**
     * 关注用户
     *
     * @param startUserId
     * @param endUserId
     */
    boolean followUser(int startUserId, int endUserId);

    boolean isFollowed(int startUserId, int endUserId);
}
