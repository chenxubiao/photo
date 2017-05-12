package cn.chenxubiao.project.service;

/**
 * Created by chenxb on 17-4-1.
 */
public interface ProjectLikeService {
    void disposeLike(int userId, int projectId);

    int countByUserIdAndProjectId(int userId, int projectId);

    int countProjectLikeNum(int projectId);

    int countByPicOwner(int userId);
}
