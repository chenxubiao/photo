package cn.chenxubiao.project.bean;

import cn.chenxubiao.picture.domain.PictureExif;
import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.user.bean.UserInfoBean;

/**
 * Created by chenxb on 17-5-1.
 */
public class ProjectBean {
    private int projectId;  //项目id
    private int userId;
    private int isSelf;     //是否为自己
    private int isFollow;   //是否关注
    private int viewNum;
    private int likeNum;
    private int liked;      //是否点赞
    private int picId;      //图片id
    private int width;
    private int height;
    private String title;
    private UserInfoBean user;

    public ProjectBean() {
    }

    public ProjectBean(ProjectInfo projectInfo, int likeNum, int viewNum, PictureExif exif) {
        this.width = exif.getWidth();
        this.height = exif.getHeight();
        this.projectId = projectInfo.getId();
        this.picId = projectInfo.getPicId();
        this.userId = projectInfo.getUserId();
        this.title = projectInfo.getTitle();
        this.likeNum = likeNum;
        this.viewNum = viewNum;
    }

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(int isSelf) {
        this.isSelf = isSelf;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public UserInfoBean getUser() {
        return user;
    }

    public void setUser(UserInfoBean user) {
        this.user = user;
    }
}
