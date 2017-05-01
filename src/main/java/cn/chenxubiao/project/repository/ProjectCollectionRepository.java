package cn.chenxubiao.project.repository;

import cn.chenxubiao.project.domain.TrendProjectCollection;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by chenxb on 17-4-1.
 */
@Repository
@Transactional
public interface TrendProjectCollectionRepository extends PagingAndSortingRepository<TrendProjectCollection, Long> {
}
