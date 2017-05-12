package cn.chenxubiao.project.service;

import cn.chenxubiao.project.domain.ProjectTag;

import java.util.List;

/**
 * Created by chenxb on 17-4-1.
 */
public interface ProjectTagService {

    void saveAll(List<ProjectTag> projectTagList);

    List<ProjectTag> findByProjectId(int projectId);

    void deleteAll(List<ProjectTag> projectTagList);
}
