package cn.chenxubiao.picture.repository;

import cn.chenxubiao.picture.domain.PictureAttachment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenxb on 17-4-1.
 */
@Repository
@Transactional
public interface PictureAttachmentRepository extends PagingAndSortingRepository<PictureAttachment, Long> {

    PictureAttachment findById(int id);

    @Query(value = "select count (a) from PictureAttachment a where id = ?1")
    int countById(int id);
}
