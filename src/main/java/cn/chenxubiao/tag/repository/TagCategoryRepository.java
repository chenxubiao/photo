package cn.chenxubiao.tag.repository;

import cn.chenxubiao.tag.domain.TagCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by chenxb on 17-4-29.
 */
@Repository
public interface TagCategoryRepository extends PagingAndSortingRepository<TagCategory, Long> {

    @Query(value = "select count (a) from TagCategory a ")
    int countCategory();

    @Query(value = "select a from TagCategory a")
    List<TagCategory> findAllTagCategory();

    TagCategory findById(int id);

    int countById(int id);

    List<TagCategory> findDistinctByNameLike(String name);
}
