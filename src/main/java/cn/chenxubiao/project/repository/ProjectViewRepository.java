package cn.chenxubiao.project.repository;

import cn.chenxubiao.project.domain.ProjectView;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by chenxb on 17-5-9.
 */
@Repository
public interface ProjectViewRepository extends PagingAndSortingRepository<ProjectView, Long> {

    int countViewNumByProjectId(int projectId);

    int countViewNumByUserId(int userId);

    int countByViewer(int viewer);

    List<ProjectView> findAllByUserIdIsNot(int userId);
}
