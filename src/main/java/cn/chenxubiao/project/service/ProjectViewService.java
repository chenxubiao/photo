package cn.chenxubiao.project.service;

import cn.chenxubiao.project.domain.ProjectView;

import java.util.List;

/**
 * Created by chenxb on 17-5-9.
 */
public interface ProjectViewService {
    void save(ProjectView projectView);

    int countByProjectId(int projectId);

    int countUserPicViewNum(int userId);

    int countByViewer(int viewer);

    List<ProjectView> findAllByUserIdIsNot(int userId);
}
