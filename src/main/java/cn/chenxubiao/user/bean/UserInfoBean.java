package cn.chenxubiao.user.bean;

import cn.chenxubiao.user.domain.UserInfo;

import java.util.Date;

/**
 * Created by chenxb on 17-5-1.
 */
public class UserInfoBean {
    private int userId;
    private int avatarId;
    private int backgroundId;
    private String userName;
    private String email;
    private String cellphone;   //被关注者id，以 , 隔开
    private int sex;            //性别
    private Date birthday;      //出生日期
    private String description; //简介
    private int follows;        //userId的粉丝
    private int following;      //关注
    private int isFollow;       //userSesion 是否关注

    public UserInfoBean() {
    }

    public UserInfoBean(UserInfo userInfo) {
        this.userId = userInfo.getId();
        this.userName = userInfo.getUserName();
        this.avatarId = userInfo.getAvatarId();
        this.backgroundId = userInfo.getBackgroundId();
        this.email = userInfo.getEmail();
        this.cellphone = userInfo.getCellphone();
        this.sex = userInfo.getSex();
        this.birthday = userInfo.getBirthday();
        this.description = userInfo.getDescription();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public void setIsFollow(int isFollow) {

        this.isFollow = isFollow;
    }
}
