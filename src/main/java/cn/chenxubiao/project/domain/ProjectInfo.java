package cn.chenxubiao.project.domain;

import cn.chenxubiao.common.utils.DateStringFormatUtil;
import cn.chenxubiao.project.bean.ProjectInfoBean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by chenxb on 17-4-27.
 */
@Entity
@Table(name = "bbs_project_info")
public class ProjectInfo implements Serializable{

    private static final long serialVersionUID = 5587897889569281257L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int userId;     //创建者id
    private int picId;      //图片id
    private String title;   //图片标题
    private int categoryId; //分类id
    private int auth;
    private int money;
    private String description = ""; //介绍
    @Column(name = "createTime", updatable = false)
    private Date createTime;
    @Column(name = "modifyTime")
    private Date modifyTime;
    @Transient
    private String time;

    public ProjectInfo(){

    }

    public ProjectInfo(ProjectInfoBean projectInfoBean) {
        this.id = projectInfoBean.getId();
        this.title = projectInfoBean.getTitle();
        this.categoryId = projectInfoBean.getCategoryId();
        this.picId = projectInfoBean.getPicId();
        this.description = projectInfoBean.getDescription();
        this.userId = projectInfoBean.getUserId();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getTime() {
        return DateStringFormatUtil.format(this.createTime);
    }
}
