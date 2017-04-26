package cn.chenxubiao.user.service;

import cn.chenxubiao.user.domain.UserRole;
import cn.chenxubiao.user.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenxb on 17-4-1.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> findListByUserId(int userId) {
        if (userId <= 0) {
            return null;
        }
        return userRoleRepository.findUserRolesByUserId(userId);
    }
}
