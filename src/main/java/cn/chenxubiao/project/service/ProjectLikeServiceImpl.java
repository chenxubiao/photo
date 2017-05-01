package cn.chenxubiao.project.service;

import cn.chenxubiao.project.domain.ProjectLike;
import cn.chenxubiao.project.repository.ProjectLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenxb on 17-4-1.
 */
@Service
public class ProjectLikeServiceImpl implements ProjectLikeService {
    @Autowired
    private ProjectLikeRepository projectLikeRepository;

    @Override
    public void disposeLike(int userId, int projectId) {
        if (userId <= 0 || projectId <= 0) {
            return;
        }
        ProjectLike projectLike = projectLikeRepository.findByUserIdAndProjectId(userId, projectId);
        if (projectLike == null) {
            projectLikeRepository.save(projectLike);
        } else {
            projectLikeRepository.delete(projectLike);
        }
    }

    @Override
    public int countByUserIdAndProjectId(int userId, int projectId) {
        if (userId <= 0 || projectId <= 0) {
            return 0;
        }
        return projectLikeRepository.countByUserIdAndProjectId(userId, projectId);
    }
}
