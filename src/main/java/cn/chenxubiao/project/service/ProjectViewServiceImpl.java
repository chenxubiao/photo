package cn.chenxubiao.project.service;

import cn.chenxubiao.project.domain.ProjectView;
import cn.chenxubiao.project.repository.ProjectViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenxb on 17-5-9.
 */
@Service
public class ProjectViewServiceImpl implements ProjectViewService {
    @Autowired
    private ProjectViewRepository projectViewRepository;

    @Override
    public void save(ProjectView projectView) {
        if (projectView == null) {
            return;
        }
        projectViewRepository.save(projectView);
    }

    @Override
    public int countByProjectId(int projectId) {
        if (projectId <= 0) {
            return 0;
        }
        return projectViewRepository.countViewNumByProjectId(projectId);
    }

    @Override
    public int countUserPicViewNum(int userId) {
        if (userId <= 0) {
            return 0;
        }
        return projectViewRepository.countViewNumByUserId(userId);
    }

    @Override
    public int countByViewer(int viewer) {
        if (viewer <= 0) {

            return 0;
        }
        return projectViewRepository.countByViewer(viewer);
    }
}
