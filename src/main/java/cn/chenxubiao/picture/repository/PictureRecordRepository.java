package cn.chenxubiao.picture.repository;

import cn.chenxubiao.picture.domain.PictureRecord;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenxb on 17-4-1.
 */
@Repository
@Transactional
public interface PictureRecordRepository extends PagingAndSortingRepository<PictureRecord, Long> {

}
