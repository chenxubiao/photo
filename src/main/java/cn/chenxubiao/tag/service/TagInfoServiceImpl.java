package cn.chenxubiao.tag.service;

import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.tag.domain.TagInfo;
import cn.chenxubiao.tag.repository.TagInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenxb on 17-4-1.
 */
@Service
public class TagInfoServiceImpl implements TagInfoService {
    @Autowired
    private TagInfoRepository tagInfoRepository;

    @Override
    public TagInfo findByIdAndNormal(int id) {
        if (id <= 0) {
            return null;
        }
        return tagInfoRepository.findByIdAndStatus(id, BBSConsts.TagInfoStatus.NORMAL);
    }
}
