package cn.chenxubiao.user.service;

import cn.chenxubiao.user.domain.UserRole;

import java.util.List;

/**
 * Created by chenxb on 17-4-1.
 */
public interface UserRoleService {
    List<UserRole> findListByUserId(int userId);
}
