package cn.chenxubiao.project.repository;

import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.project.domain.TrendProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenxb on 17-4-1.
 */
@Repository
@Transactional
public interface ProjectInfoRepository extends PagingAndSortingRepository<ProjectInfo, Long> {

    @Query(value = "select a from ProjectInfo a where a.picId = ?1")
    ProjectInfo findByPicId(int picId);

    @Query(value = "select a from ProjectInfo a")
    Page<ProjectInfo> findByPage(Pageable pageable);

    @Query(value = "select a from ProjectInfo a where a.userId = ?1")
    Page<ProjectInfo> findByUserIdAndPage(int userId, Pageable pageable);
}
