package cn.chenxubiao.project.bean;

import cn.chenxubiao.project.domain.ProjectInfo;

/**
 * Created by chenxb on 17-5-1.
 */
public class ProjectBean {
    private int projectId;  //项目id
    private int userId;
    private int like;       //是否点赞
    private int picId;      //图片id


    public ProjectBean() {
    }

    public ProjectBean(ProjectInfo projectInfo) {
        this.projectId = projectInfo.getId();
        this.picId = projectInfo.getPicId();
        this.userId = projectInfo.getUserId();

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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }
}
