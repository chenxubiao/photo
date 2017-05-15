package cn.chenxubiao.user.bean;

/**
 * Created by chenxb on 17-5-14.
 */
public class UserBean {
    private int userId;
    private int avatarId;
    private String userName;
    private int following;
    private int follows;
    private int viewOthers;
    private int otherViews;
    private int picNum;
    private int likeNum;
    private int money;

    public UserBean() {
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

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getViewOthers() {
        return viewOthers;
    }

    public void setViewOthers(int viewOthers) {
        this.viewOthers = viewOthers;
    }

    public int getOtherViews() {
        return otherViews;
    }

    public void setOtherViews(int otherViews) {
        this.otherViews = otherViews;
    }

    public int getPicNum() {
        return picNum;
    }

    public void setPicNum(int picNum) {
        this.picNum = picNum;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
