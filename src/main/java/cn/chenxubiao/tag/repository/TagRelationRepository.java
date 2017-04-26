package cn.chenxubiao.tag.repository;

import cn.chenxubiao.tag.domain.TagRelation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenxb on 17-4-1.
 */
@Repository
@Transactional
public interface TagRelationRepository extends PagingAndSortingRepository<TagRelation, Long> {

}
