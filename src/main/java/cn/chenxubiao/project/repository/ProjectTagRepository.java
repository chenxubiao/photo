package cn.chenxubiao.project.repository;

import cn.chenxubiao.project.domain.ProjectTag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by chenxb on 17-4-1.
 */
@Repository
@Transactional
public interface ProjectTagRepository extends PagingAndSortingRepository<ProjectTag, Long> {

    @Query(value = "select a from ProjectTag a where a.projectId = ?1")
    List<ProjectTag> findByProjectId(int projectId);

}
