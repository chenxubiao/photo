package cn.chenxubiao.tag.service;

import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.tag.domain.TagCategory;
import cn.chenxubiao.tag.repository.TagCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenxb on 17-4-29.
 */
@Service
public class TagCategoryServiceImpl implements TagCategoryService {
    @Autowired
    private TagCategoryRepository tagCategoryRepository;
    @Override
    public void saveAll(List<TagCategory> categoryList) {
        if (CollectionUtil.isEmpty(categoryList)) {
            return;
        }
        tagCategoryRepository.save(categoryList);
    }

    @Override
    public int count() {
        return tagCategoryRepository.countCategory();
    }

    @Override
    public List<TagCategory> findAll() {
        return tagCategoryRepository.findAllTagCategory();
    }

    @Override
    public TagCategory findById(int id) {
        if (id <= 0) {
            return null;
        }
        return tagCategoryRepository.findById(id);
    }
}
