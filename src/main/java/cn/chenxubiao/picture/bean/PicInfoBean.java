package cn.chenxubiao.picture.bean;

import cn.chenxubiao.picture.domain.PictureExif;
import cn.chenxubiao.user.domain.UserInfo;

/**
 * Created by chenxb on 17-4-28.
 */
public class PicInfoBean {
    private int picId;      //图片id
    private int userId;     //用户id
    private String userName;//用户名
    private int avatarId;   //用户头像id
    private int width;      //图片宽度
    private int height;     //图片高度
    private int liked;      //图片名称

    public PicInfoBean(){

    }

    public PicInfoBean(PictureExif pictureExif, int liked, UserInfo user) {
        this.picId = pictureExif.getPicId();
        this.width = pictureExif.getWidth();
        this.height = pictureExif.getHeight();
        this.liked = liked;
        this.userId = user.getId();
        this.avatarId = user.getAvatarId();
        this.userName = user.getUserName();
    }
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }
}
