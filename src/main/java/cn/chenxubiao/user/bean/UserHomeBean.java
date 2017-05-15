package cn.chenxubiao.user.bean;

import cn.chenxubiao.project.bean.ProjectBean;
import cn.chenxubiao.user.domain.UserInfo;

import java.util.List;

/**
 * Created by chenxb on 17-5-1.
 */
public class UserHomeBean extends UserInfoBean {
    private int followers;  //被关注
    private int following;  //正在关注
    private int isSelf;     //是否为自己
    private int isFollow;   //如果不是自己，是否关注了ta
    private int views;      //被多少人查看
    private int likes;      //被多少人喜欢
    private List<ProjectBean> project;
    private UserProfileBean userProfile;

    public UserHomeBean() {

    }

    public UserHomeBean(int likes, int views, int following, int followers, UserInfo userInfo, List<ProjectBean> project) {
        this.likes = likes;
        super.setUserId(userInfo.getId());
        super.setAvatarId(userInfo.getAvatarId());
        super.setBackgroundId(userInfo.getBackgroundId());
        super.setSex(userInfo.getSex());
        super.setDescription(userInfo.getDescription());
        super.setEmail(userInfo.getEmail());
        super.setBirthday(userInfo.getBirthday());
        super.setUserName(userInfo.getUserName());
        this.project = project;
        this.following = following;
        this.followers = followers;
        this.views = views;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public List<ProjectBean> getProject() {
        return project;
    }

    public void setProject(List<ProjectBean> project) {
        this.project = project;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
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

    public UserProfileBean getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileBean userProfile) {
        this.userProfile = userProfile;
    }
}
