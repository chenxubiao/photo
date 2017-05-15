package cn.chenxubiao.project.service;

import cn.chenxubiao.project.domain.ProjectLike;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by chenxb on 17-4-1.
 */
public interface ProjectLikeService {
    void disposeLike(int userId, int projectId);

    int isLiked(int userId, int projectId);

    int countProjectLikeNum(int projectId);

    int countByPicOwner(int userId);

    List<ProjectLike> findPopular(Pageable pageable);
}
