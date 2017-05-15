package cn.chenxubiao.user.service;

import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.user.domain.UserTool;
import cn.chenxubiao.user.repository.UserToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by chenxb on 17-5-11.
 */
@Service
@Transactional
public class UserToolServiceImpl implements UserToolService {
    @Autowired
    private UserToolRepository userToolRepository;

    @Override
    public boolean isExist(int userId, String name, int type) {
        if (StringUtil.isBlank(name) || type <= 0 || userId <= 0) {
            return false;
        }
        return userToolRepository.countByUserIdAndNameAndType(userId, name, type) > 0;
    }

    @Override
    public void save(UserTool userTool) {
        if (userTool == null) {
            return;
        }
        userToolRepository.save(userTool);
    }

    @Override
    public List<UserTool> findByUserId(int userId) {
        if (userId <= 0) {
            return null;
        }
        return userToolRepository.findByUserId(userId);
    }

    @Override
    public void deleteNotInNameList(int userId, int type, List<String> saved) {
        if (userId <= 0 || type <= 0 || CollectionUtil.isEmpty(saved)) {
            return;
        }
        userToolRepository.deleteAllByUserIdAndTypeAndNameNotIn(userId, type, saved);
    }

    @Override
    public void deleteAllByType(int userId, int type) {
        if (userId <= 0 || type <= 0) {
            return;
        }
        userToolRepository.deleteAllByUserIdAndType(userId, type);
    }

    @Override
    public void saveAll(List<UserTool> userToolList) {
        if (CollectionUtil.isEmpty(userToolList)) {
            return;
        }
        userToolRepository.save(userToolList);
    }
}
