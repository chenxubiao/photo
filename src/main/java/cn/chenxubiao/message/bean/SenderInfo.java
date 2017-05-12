package cn.chenxubiao.message.bean;

import cn.chenxubiao.user.domain.UserInfo;

/**
 * Created by chenxb on 17-5-11.
 */
public class SenderInfo {
    private int userId;
    private int avatarId;
    private String userName;

    public SenderInfo() {
    }

    public SenderInfo(UserInfo userInfo) {
        this.userId = userInfo.getId();
        this.avatarId = userInfo.getAvatarId();
        this.userName = userInfo.getUserName();
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
