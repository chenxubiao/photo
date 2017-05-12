package cn.chenxubiao.tag.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.tag.domain.TagInfo;
import cn.chenxubiao.tag.service.TagInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by chenxb on 17-5-9.
 */
@RestController
public class TagInfoController extends CommonController {

    @Autowired
    private TagInfoService tagInfoService;

    @RequestMapping(value = "/tag/info/list", method = RequestMethod.GET)
    public ResponseEntity getTagListData() {

        List<TagInfo> tagInfoList = tagInfoService.findAll();
        return ResponseEntity.success().set(BBSConsts.DATA, tagInfoList);
    }
}
