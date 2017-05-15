package cn.chenxubiao.tag.web;

import cn.chenxubiao.account.domain.Account;
import cn.chenxubiao.account.service.AccountService;
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
    @Autowired
    private AccountService accountService;

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

    @RequestMapping(value = "/tag/category/init")
    public ResponseEntity initTagCategory() {

        int count = tagCategoryService.count();
        if (count > 0) {
            List<TagCategory> tagCategoryListDB = tagCategoryService.findAll();
            return ResponseEntity.success().set(BBSConsts.DATA, tagCategoryListDB);
        }

        Account account = new Account();
        account.setUserId(0);
        account.setCreateTime(new Date());
        account.setModifyTime(account.getCreateTime());
        account.setTotalMoney(10000);
        accountService.save(account);

        List<String> categoryList = new ArrayList<>();
        categoryList.add("动物");
        categoryList.add("黑白");
        categoryList.add("名人");
        categoryList.add("城市与建筑");
        categoryList.add("商业");
        categoryList.add("演唱会");
        categoryList.add("家庭");
        categoryList.add("时尚");
        categoryList.add("电影");
        categoryList.add("美术");
        categoryList.add("食物");
        categoryList.add("新闻");
        categoryList.add("风景");
        categoryList.add("大镜头");
        categoryList.add("自然");
        categoryList.add("裸体");
        categoryList.add("人像");
        categoryList.add("表演艺术");
        categoryList.add("运动");
        categoryList.add("静物");
        categoryList.add("街拍");
        categoryList.add("交通工具");
        categoryList.add("水下");
        categoryList.add("探险");
        categoryList.add("婚礼");
        List<TagCategory> tagCategoryList = new ArrayList<>();
        for (String categoryString : categoryList) {
            TagCategory tagCategory = new TagCategory();
            tagCategory.setName(categoryString);
            tagCategory.setCreateTime(new Date());
            tagCategory.setModifyTime(tagCategory.getCreateTime());
            tagCategoryList.add(tagCategory);
        }
        tagCategoryService.saveAll(tagCategoryList);
        return ResponseEntity.success().set(BBSConsts.DATA, tagCategoryList);
    }

}
