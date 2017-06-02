package cn.chenxubiao.user.service;

import cn.chenxubiao.user.bean.UserInfoBean;
import cn.chenxubiao.user.domain.UserInfo;

import java.util.List;

/**
 * Created by chenxb on 17-4-1.
 */
public interface UserInfoService {

    UserInfo findByEmail(String email);

    UserInfo findByCellphone(String cellphone);

    UserInfo findByUserName(String userName);

    UserInfo loginByEmail(String email, String password);

    UserInfo loginByCellphone(String cellphone, String password);

    UserInfo loginByUserName(String userName, String password);

    UserInfo save(UserInfo userInfo);

    boolean isEmailExist(String email);

    boolean isCellphoneExist(String cellphone);

    boolean isUserNameExist(String userName);

    UserInfo findById(int id);

    UserInfo findByIdAndNormal(int id);

    List<UserInfoBean> search(String name);

    List<UserInfoBean> search(String name, int userId);

    UserInfoBean convertToUserInfoBean(UserInfo userInfo);

    /**
     * 将userInfo转换为userInfoBean,并求出userId是否关注user
     *
     * @param userInfo
     * @param userId
     * @return
     */
    UserInfoBean convertToUserInfoBean(UserInfo userInfo, int userId);

    List<UserInfo> findPopular(int removeUserId);

    List<UserInfo> findIdNotIn(List<Integer> ids);

    List<UserInfo> findByIdIn(List<Integer> ids);
}
