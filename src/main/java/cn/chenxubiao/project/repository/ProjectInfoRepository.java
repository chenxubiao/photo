package cn.chenxubiao.project.repository;

import cn.chenxubiao.project.domain.ProjectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface ProjectInfoRepository extends PagingAndSortingRepository<ProjectInfo, Long> {

    @Query(value = "select a from ProjectInfo a where a.picId = ?1")
    ProjectInfo findByPicId(int picId);

    @Query(value = "select a from ProjectInfo a order by a.id desc ")
    Page<ProjectInfo> findByPage(Pageable pageable);


    @Query(value = "select a from ProjectInfo a where a.userId = ?1")
    Page<ProjectInfo> findByUserIdAndPage(int userId, Pageable pageable);

    @Query(value = "select a from ProjectInfo a where a.userId = ?2 and a.id < ?1")
    Page<ProjectInfo> findFormerProjectInfo(int id, int userId, Pageable pageable);

    @Query(value = "select a from ProjectInfo a where a.userId = ?2 and a.id > ?1")
    Page<ProjectInfo> findLatterProjectInfo(int id, int userId, Pageable pageable);

    List<ProjectInfo> findByUserId(int userId);

    ProjectInfo findById(int id);

    Page<ProjectInfo> findAllByUserIdInOrderByIdDesc(List<Integer> userId, Pageable pageable);

    List<ProjectInfo> findDistinctByTitleLikeOrDescriptionLikeOrderByIdDesc(String title, String description);

    int countByUserId(int userId);

    ProjectInfo findFirstByUserIdAndIdBeforeOrderByIdDesc(int userId, int id);

    ProjectInfo findFirstByUserIdAndIdAfter(int userId, int id);

    List<ProjectInfo> findAllByCategoryIdInOrderByIdDesc(List<Integer> categoryIds);

    List<ProjectInfo> findAllByCategoryIdNotInAndIdInOrderByIdDesc(List<Integer> cateogryIds, List<Integer> ids);

    List<ProjectInfo> findAllByIdInOrderByIdDesc(List<Integer> ids);
}
