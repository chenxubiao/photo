package cn.chenxubiao.tag.service;

import cn.chenxubiao.tag.domain.TagInfo;

/**
 * Created by chenxb on 17-4-1.
 */
public interface TagInfoService {
    TagInfo findByIdAndNormal(int id);
}
