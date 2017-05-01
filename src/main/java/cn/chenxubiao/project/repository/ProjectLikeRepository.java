package cn.chenxubiao.project.repository;

import cn.chenxubiao.project.domain.ProjectLike;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenxb on 17-4-1.
 */
@Repository
@Transactional
public interface ProjectLikeRepository extends PagingAndSortingRepository<ProjectLike, Long> {

    @Query(value = "select a from ProjectLike a where a.userId = ?1 and a.projectId = ?2")
    ProjectLike findByUserIdAndProjectId(int userId, int projectId);

    @Query(value = "select count(a) from ProjectLike a where a.userId = ?1 and a.projectId = ?2")
    int countByUserIdAndProjectId(int userId, int projectId);
}
