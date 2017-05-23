package cn.chenxubiao.tag.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.tag.domain.TagCategory;
import cn.chenxubiao.tag.service.TagCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenxb on 17-4-29.
 */
@RestController
public class TagCategoryController extends CommonController {
    @Autowired
    private TagCategoryService tagCategoryService;

    @RequestMapping(value = "/tag/category/list/data", method = RequestMethod.GET)
    public ResponseEntity getCategoryList(HttpServletRequest request) {
        List<TagCategory> tagCategoryList = tagCategoryService.findAll();
        List<TagCategory> categoryList = new ArrayList<>();
        TagCategory category = new TagCategory();
        category.setId(0);
        category.setName("未知分类");
        category.setCreateTime(new Date());
        category.setModifyTime(category.getCreateTime());
        categoryList.add(category);
        if (CollectionUtil.isNotEmpty(tagCategoryList)) {
            categoryList.addAll(tagCategoryList);
        }
        return ResponseEntity.success().set(BBSConsts.DATA, categoryList);
    }

}
