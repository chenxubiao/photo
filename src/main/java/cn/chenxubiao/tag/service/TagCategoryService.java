package cn.chenxubiao.tag.service;

import cn.chenxubiao.tag.domain.TagCategory;

import java.util.List;

/**
 * Created by chenxb on 17-4-29.
 */
public interface TagCategoryService {
    void saveAll(List<TagCategory> categoryList);

    int count();

    List<TagCategory> findAll();

    TagCategory findById(int id);
}
