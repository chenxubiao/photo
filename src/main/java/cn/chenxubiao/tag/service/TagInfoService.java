package cn.chenxubiao.tag.service;

import cn.chenxubiao.tag.domain.TagInfo;

import java.util.List;

/**
 * Created by chenxb on 17-4-1.
 */
public interface TagInfoService {
    TagInfo findById(int id);

    List<TagInfo> findInIds(List<Integer> ids);

    TagInfo save(TagInfo tagInfo);

    List<TagInfo> findAll();
}
