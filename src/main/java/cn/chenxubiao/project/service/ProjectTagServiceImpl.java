package cn.chenxubiao.project.service;

import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.project.domain.ProjectTag;
import cn.chenxubiao.project.repository.ProjectTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenxb on 17-4-1.
 */
@Service
public class ProjectTagServiceImpl implements ProjectTagService {
    @Autowired
    private ProjectTagRepository projectTagRepository;

    @Override
    public void saveAll(List<ProjectTag> projectTagList) {
        if (CollectionUtil.isEmpty(projectTagList)) {
            return;
        }
        projectTagRepository.save(projectTagList);
    }
}
