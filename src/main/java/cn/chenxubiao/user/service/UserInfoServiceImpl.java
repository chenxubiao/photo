package cn.chenxubiao.user.service;

import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.domain.UserRole;
import cn.chenxubiao.user.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenxb on 17-4-1.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public UserInfo loginByEmail(String email, String password) {
        if (StringUtil.isEmpty(email) || StringUtil.isEmpty(password)) {
            return null;
        }
        UserInfo userInfo = userInfoRepository.findByEmailAndPassword(email, password);
        return setUserRoleList(userInfo);
    }

    @Override
    public UserInfo loginByCellphone(String cellphone, String password) {
        if (StringUtil.isEmpty(cellphone) || StringUtil.isEmpty(password)) {
            return null;
        }
        UserInfo userInfo = userInfoRepository.findByCellphoneAndPassword(cellphone, password);
        return setUserRoleList(userInfo);
    }

    @Override
    public UserInfo loginByUserName(String userName, String password) {
        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)) {
            return null;
        }
        UserInfo userInfo = userInfoRepository.findByUserNameAndPassword(userName, password);
        return setUserRoleList(userInfo);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        }
        userInfo = userInfoRepository.save(userInfo);
        return setUserRoleList(userInfo);
    }

    @Override
    public boolean isEmailExist(String email) {
        if (StringUtil.isBlank(email)) {
            return false;
        }
        return userInfoRepository.countByEmail(email) > 0;
    }

    @Override
    public boolean isCellphoneExist(String cellphone) {
        if (StringUtil.isBlank(cellphone)) {
            return false;
        }
        return userInfoRepository.countByCellphone(cellphone) > 0;
    }

    @Override
    public boolean isUserNameExist(String userName) {
        if (StringUtil.isBlank(userName)) {
            return false;
        }
        return userInfoRepository.countByUserName(userName) > 0;
    }

    @Override
    public UserInfo findById(int id) {
        if (id <= 0) {
            return null;
        }
        UserInfo userInfo = userInfoRepository.findById(id);
        if (userInfo == null) {
            return null;
        }
        return setUserRoleList(userInfo);
    }

    @Override
    public UserInfo findByIdAndNormal(int id) {
        if (id <= 0) {
            return null;
        }
        UserInfo userInfo = userInfoRepository.findByIdAndStatus(id, BBSConsts.UserStatus.USER_IS_NORMAL);
        return setUserRoleList(userInfo);
    }

    private UserInfo setUserRoleList(UserInfo userInfo) {
        if (userInfo == null) {
            return userInfo;
        }
        List<UserRole> userRoleList = userRoleService.findListByUserId(userInfo.getId());
        userInfo.setUserRoleList(userRoleList);
        return userInfo;
    }

}
