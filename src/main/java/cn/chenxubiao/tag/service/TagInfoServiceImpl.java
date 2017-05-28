package cn.chenxubiao.tag.service;

import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.tag.domain.TagInfo;
import cn.chenxubiao.tag.repository.TagInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenxb on 17-4-1.
 */
@Service
public class TagInfoServiceImpl implements TagInfoService {
    @Autowired
    private TagInfoRepository tagInfoRepository;

    @Override
    public TagInfo findById(int id) {
        if (id <= 0) {
            return null;
        }
        return tagInfoRepository.findById(id);
    }

    @Override
    public List<TagInfo> findInIds(List<Integer> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return null;
        }
        return tagInfoRepository.findByIdIn(ids);
    }

    @Override
    public TagInfo save(TagInfo tagInfo) {
        if (tagInfo == null) {
            return tagInfo;
        }
        TagInfo tagInfoDB = tagInfoRepository.findByName(tagInfo.getName());
        if (tagInfoDB != null) {
            return tagInfoDB;
        }
        return tagInfoRepository.save(tagInfo);
    }

    @Override
    public List<TagInfo> findAll() {
        return tagInfoRepository.findAllTagInfo();
    }

    @Override
    public List<TagInfo> search(String name) {
        if (StringUtil.isEmpty(name)) {
            return null;
        }
        return tagInfoRepository.findAllByNameLike(name);
    }
}
