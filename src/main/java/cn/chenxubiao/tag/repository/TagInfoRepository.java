package cn.chenxubiao.tag.repository;

import cn.chenxubiao.tag.domain.TagInfo;
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
public interface TagInfoRepository extends PagingAndSortingRepository<TagInfo, Long> {
    TagInfo findById(int id);

    List<TagInfo> findByIdIn(List<Integer> ids);

    TagInfo findByName(String name);

    @Query(value = "select a from TagInfo a where a.id>0")
    List<TagInfo> findAllTagInfo();

}
