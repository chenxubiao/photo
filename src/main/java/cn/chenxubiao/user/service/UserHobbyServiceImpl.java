package cn.chenxubiao.user.service;

import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.tag.domain.TagInfo;
import cn.chenxubiao.tag.service.TagInfoService;
import cn.chenxubiao.user.domain.UserHobby;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.repository.UserHobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public boolean like(int categoryId, int userId) {
        if (categoryId <= 0 || userId <= 0) {
            return false;
        }
        TagInfo tagInfo = tagInfoService.findById(categoryId);
        if (tagInfo == null) {
            return false;
        }
        UserInfo userInfo = userInfoService.findByIdAndNormal(userId);
        if (userInfo == null) {
            return false;
        }
//        int count = userHobbyRepository.countBycategoryIdAndUserId(categoryId, userId);
//        if (count > 0) {
//            return false;
//        }
        UserHobby userHobby = new UserHobby();
        userHobby.setCategoryId(categoryId);
        userHobby.setUserId(userId);
        userHobby.setCreateTime(new Date());
        userHobby.setModifyTime(userHobby.getCreateTime());
        userHobbyRepository.save(userHobby);
        return true;
    }

    @Override
    public boolean isExist(int categoryId, int userId) {
        if (categoryId <= 0 || userId <= 0) {
            return false;
        }
        return userHobbyRepository.countByCategoryIdAndUserId(categoryId, userId) > 0;
    }

    @Override
    public void saveAll(List<UserHobby> userHobbyList) {
        if (CollectionUtil.isEmpty(userHobbyList)) {
            return;
        }
        userHobbyRepository.save(userHobbyList);
    }

    @Override
    public List<UserHobby> findByUserId(int userId) {
        if (userId <= 0) {
            return null;
        }
        return userHobbyRepository.findByUserId(userId);
    }

    @Override
    public void deleteNotInCategoryIds(int userId, List<Integer> categoryIds) {
        if (userId <= 0 || CollectionUtil.isEmpty(categoryIds)) {
            return;
        }
        userHobbyRepository.deleteAllByUserIdAndAndCategoryIdNotIn(userId, categoryIds);
    }

    @Override
    public List<UserHobby> findInCategoryId(List<Integer> categoryIds) {
        if (CollectionUtil.isEmpty(categoryIds)) {
            return null;
        }
        return userHobbyRepository.findDistinctByCategoryIdIn(categoryIds);
    }

}
