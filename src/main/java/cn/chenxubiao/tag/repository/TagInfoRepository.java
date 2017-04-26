package cn.chenxubiao.tag.repository;

import cn.chenxubiao.tag.domain.TagInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenxb on 17-4-1.
 */
@Repository
@Transactional
public interface TagInfoRepository extends PagingAndSortingRepository<TagInfo, Long> {
    TagInfo findById(int id);

    TagInfo findByIdAndStatus(int id, int status);
}
