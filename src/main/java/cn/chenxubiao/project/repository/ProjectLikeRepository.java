package cn.chenxubiao.project.repository;

import cn.chenxubiao.project.domain.ProjectLike;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.List;

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

    int countByProjectId(int projectId);

    int countByProjectIdIn(List<Integer> projectIds);

    @Query(value = "select distinct a.userId ,COUNT(a.projectId) as b from ProjectLike as a GROUP BY a.userId order by b desc")
    List<Object[]> findPopular();

    @Query(value = "select a from ProjectLike a")
    List<ProjectLike> findAll();

}
