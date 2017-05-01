package cn.chenxubiao.project.bean;

/**
 * Created by chenxb on 17-4-30.
 */
public class ProjectInfoBean {
    private int id;
    private int userId;     //创建者id
    private int picId;      //图片id
    private int categoryId; //分类id
    private String tagIds;  //标签id，以「,」分隔
    private String description; //介绍

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
