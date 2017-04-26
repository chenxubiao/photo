package cn.chenxubiao.user.service;

import cn.chenxubiao.tag.domain.TagInfo;
import cn.chenxubiao.tag.service.TagInfoService;
import cn.chenxubiao.user.domain.UserHobby;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.repository.UserHobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by chenxb on 17-4-1.
 */
@Service
public class UserHobbyServiceImpl implements UserHobbyService {
    @Autowired
    private UserHobbyRepository userHobbyRepository;
    @Autowired
    private TagInfoService tagInfoService;
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public boolean like(int tagId, int userId) {
        if (tagId <= 0 || userId <= 0) {
            return false;
        }
        TagInfo tagInfo = tagInfoService.findByIdAndNormal(tagId);
        if (tagInfo == null) {
            return false;
        }
        UserInfo userInfo = userInfoService.findByIdAndNormal(userId);
        if (userInfo == null) {
            return false;
        }
        int count = userHobbyRepository.countByTagIdAndUserId(tagId, userId);
        if (count > 0) {
            return false;
        }
        UserHobby userHobby = new UserHobby();
        userHobby.setTagId(tagId);
        userHobby.setUserId(userId);
        userHobby.setCreateTime(new Date());
        userHobby.setModifyTime(userHobby.getCreateTime());
        userHobbyRepository.save(userHobby);
        return true;
    }

    @Override
    public boolean isExist(int tagId, int userId) {
        if (tagId <= 0 || userId <= 0) {
            return false;
        }
        return userHobbyRepository.countByTagIdAndUserId(tagId, userId) > 0;
    }

}
