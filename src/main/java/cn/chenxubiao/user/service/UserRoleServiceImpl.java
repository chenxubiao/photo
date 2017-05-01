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

    @Override
    public void save(UserRole userRole) {
        if (userRole == null) {
            return;
        }
        boolean isExist = isExist(userRole.getUserId(), userRole.getRoleId());
        if (isExist) {
            return;
        }
        userRoleRepository.save(userRole);
    }

    @Override
    public boolean isExist(int userId, int roleId) {
        if (userId <= 0 || roleId <= 0) {
            return false;
        }
        int count = userRoleRepository.countByUserIdAndRoleId(userId, roleId);
        if (count > 0) {
            return true;
        }
        return false;
    }
}
