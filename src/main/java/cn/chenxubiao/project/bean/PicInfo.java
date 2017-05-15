package cn.chenxubiao.project.bean;

import cn.chenxubiao.common.utils.DateStringFormatUtil;
import cn.chenxubiao.picture.domain.PictureExif;
import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.user.domain.UserInfo;

/**
 * Created by chenxb on 17-5-14.
 */
public class PicInfo {
    private int userId;
    private int avatarId;
    private String userName;
    private int isSelf;
    private int follows;
    private int following;
    private int isLiked;
    private int likeNum;
    private int isFollow;
    private int picId;
    private int width;
    private int height;
    private String title;
    private String time;

    public PicInfo() {
    }

    public PicInfo(UserInfo userInfo, ProjectInfo projectInfo, PictureExif pictureExif) {
        this.width = pictureExif.getWidth();
        this.height = pictureExif.getHeight();
        this.userId = userInfo.getId();
        this.avatarId = userInfo.getAvatarId();
        this.userName = userInfo.getUserName();
        this.picId = projectInfo.getPicId();
        this.title = projectInfo.getTitle();
        this.time = DateStringFormatUtil.format(projectInfo.getCreateTime());
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

    public int getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(int isSelf) {
        this.isSelf = isSelf;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }
}
