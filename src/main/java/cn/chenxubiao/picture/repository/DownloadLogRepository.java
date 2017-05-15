package cn.chenxubiao.picture.repository;

import cn.chenxubiao.picture.domain.DownloadLog;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenxb on 17-5-14.
 */
@Repository
@Transactional
public interface DownloadLogRepository extends PagingAndSortingRepository<DownloadLog, Long> {

}
