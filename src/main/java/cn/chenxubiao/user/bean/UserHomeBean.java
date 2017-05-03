package cn.chenxubiao.user.bean;

import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.project.bean.ProjectBean;
import cn.chenxubiao.user.domain.UserInfo;

import java.util.List;

/**
 * Created by chenxb on 17-5-1.
 */
public class UserHomeBean extends UserInfoBean {
    private int followers;
    private int following;
    private List<ProjectBean> project;

    public UserHomeBean() {

    }

    public UserHomeBean(int following, int followers, UserSession userSession, List<ProjectBean> project) {
        this.followers = followers;
        this.following = following;
        super.setUserId(userSession.getUserId());
        super.setAvatarId(userSession.getAvatarId());
        super.setBackgroundId(userSession.getBackgroundId());
        super.setSex(userSession.getSex());
        super.setDescription(userSession.getDescription());
        super.setEmail(userSession.getEmail());
        super.setBirthday(userSession.getBirthday());
        super.setUserName(userSession.getUserName());
        this.project = project;
    }

    public UserHomeBean(int following, int followers, UserInfo userInfo, List<ProjectBean> project) {

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
}
