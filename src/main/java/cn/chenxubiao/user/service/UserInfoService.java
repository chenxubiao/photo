package cn.chenxubiao.user.service;

import cn.chenxubiao.user.domain.UserInfo;

/**
 * Created by chenxb on 17-4-1.
 */
public interface UserInfoService {
    UserInfo loginByEmail(String email, String password);

    UserInfo loginByCellphone(String cellphone, String password);

    UserInfo loginByUserName(String userName, String password);

    UserInfo save(UserInfo userInfo);

    boolean isEmailExist(String email);

    boolean isCellphoneExist(String cellphone);

    boolean isUserNameExist(String userName);

    UserInfo findById(int id);

    UserInfo findByIdAndNormal(int id);
}
