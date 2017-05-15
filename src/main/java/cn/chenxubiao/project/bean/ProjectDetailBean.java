package cn.chenxubiao.project.bean;

import cn.chenxubiao.common.bean.CommonBean;
import cn.chenxubiao.picture.domain.PictureExif;

import java.util.List;

/**
 * Created by chenxb on 17-5-8.
 */
public class ProjectDetailBean {
    private int projectId;  //项目id
    private int picId;      //图片id
    private int startPicId; //上一张图片id
    private int endPicId;   //下一张图片id
    private int userId;     //图片拥有者
    private int avatarId;   //
    private String userName;
    private int isSelf;     //是否为自己
    private int isFollow;   //如果不是自己，是否关注了ta
    private int liked;      //是否喜欢
    private int likeNum;    //获得喜欢数
    private int follow;     //是否关注
    private int viewNum;    //图片被产看多少次
    private CommonBean category;
    private List<CommonBean> tag;
    private String description; //说明
    private PictureExif exif;   //图片信息
    private int auth;
    private int money;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
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

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStartPicId() {
        return startPicId;
    }

    public void setStartPicId(int startPicId) {
        this.startPicId = startPicId;
    }

    public int getEndPicId() {
        return endPicId;
    }

    public void setEndPicId(int endPicId) {
        this.endPicId = endPicId;
    }

    public PictureExif getExif() {
        return exif;
    }

    public void setExif(PictureExif exif) {
        this.exif = exif;
    }

    public CommonBean getCategory() {
        return category;
    }

    public void setCategory(CommonBean category) {
        this.category = category;
    }

    public List<CommonBean> getTag() {
        return tag;
    }

    public void setTag(List<CommonBean> tag) {
        this.tag = tag;
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

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
