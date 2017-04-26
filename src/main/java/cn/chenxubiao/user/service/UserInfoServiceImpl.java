package cn.chenxubiao.user.service;

import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenxb on 17-4-1.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo loginByEmail(String email, String password) {
        if (StringUtil.isEmpty(email) || StringUtil.isEmpty(password)) {
            return null;
        }
        return userInfoRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public UserInfo loginByCellphone(String cellphone, String password) {
        if (StringUtil.isEmpty(cellphone) || StringUtil.isEmpty(password)) {
            return null;
        }
        return userInfoRepository.findByCellphoneAndPassword(cellphone, password);
    }

    @Override
    public UserInfo loginByUserName(String userName, String password) {
        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)) {
            return null;
        }
        return userInfoRepository.findByUserNameAndPassword(userName, password);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        }
        return userInfoRepository.save(userInfo);
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
        return userInfoRepository.findById(id);
    }

    @Override
    public UserInfo findByIdAndNormal(int id) {
        if (id <= 0) {
            return null;
        }
        return userInfoRepository.findByIdAndStatus(id, BBSConsts.UserStatus.USER_IS_NORMAL);
    }

}
