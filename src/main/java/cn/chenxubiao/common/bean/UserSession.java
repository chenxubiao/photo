package cn.chenxubiao.common.bean;


import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.user.domain.UserInfo;

import java.util.Date;
import java.util.Set;

/**
 * Created by chenxb on 17-3-4.
 */
public class UserSession {
    private int userId;     //用户id
    private int avatarId;   //头像id
    private int backgroundId;
    private String userName;
    private String cellphone;
    private String description;
    private Date birthday;
    private int sex;
    private int userStatus;
//    private String password;
    private int userRole;
    private String email;
//    private int userType;
    private Date createTime;
    private Date modifyTime;
    private Set<Integer> roleSet;

    public UserSession() {

    }

    public UserSession(UserInfo userInfo) {
        this.userId = userInfo.getId();
        this.userName = userInfo.getUserName();
        this.avatarId = userInfo.getAvatarId();
        this.backgroundId = userInfo.getBackgroundId();
        this.email = userInfo.getEmail();
        this.cellphone = userInfo.getCellphone();
        this.sex = userInfo.getSex();
        this.birthday = userInfo.getBirthday();
        this.userRole = userInfo.getUserRole();
        this.description = userInfo.getDescription();
        this.userStatus = userInfo.getStatus();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Set<Integer> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Integer> roleSet) {
        this.roleSet = roleSet;
    }

    public boolean isAdmin() {
        return this.userRole == BBSConsts.CRM_ADMIN;
    }

    public boolean isLogin() {
        return userId > 0;
    }

    public boolean isLocking() {
        return this.userStatus == BBSConsts.UserStatus.USER_IS_LOCKING;
    }

    public boolean isClose() {
        return this.userStatus == BBSConsts.UserStatus.USER_IS_CLOSE;
    }
}
