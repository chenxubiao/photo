package cn.chenxubiao.project.bean;

/**
 * Created by chenxb on 17-4-30.
 */
public class ProjectInfoBean {
    private int id;
    private int userId;     //创建者id
    private int picId;      //图片id
    private String title;   //图片标题
    private int categoryId; //分类id
    private String tagIds;  //标签id，以「,」分隔
    private String description = ""; //介绍
    private int auth;   //是否授权图片社区下载，0：不允许下载，1：允许下载，默认0,若允许，必须设置图片授权金额
    private int money;  //金额 >0

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
