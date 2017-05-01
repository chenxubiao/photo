package cn.chenxubiao.picture.repository;

import cn.chenxubiao.picture.domain.PictureExif;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by chenxb on 17-4-27.
 */
@Repository
@Transactional
public interface PictureExifRepository extends PagingAndSortingRepository<PictureExif, Long> {

    @Query(value = "select count(a) from PictureExif a where a.picId = ?1")
    int countByPicId(int id);

    @Query(value = "select a from PictureExif a")
    Page<PictureExif> findByPage(Pageable pageable);

    @Query(value = "select a from PictureExif a where a.picId in ?1")
    List<PictureExif> findInPicIds(Set<Integer> picIds);
}
