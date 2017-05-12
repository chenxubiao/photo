package cn.chenxubiao.user.bean;

import cn.chenxubiao.user.domain.UserInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by chenxb on 17-4-14.
 */
public class UserProfileBean {
    private String userName;
    private int avatarId;   //头像id
    private int sex;        //性别
    private Date birthday;//出生日期
    private String description;
    private List<Integer> hobby;    //cateogryId
    private String categoryIds;
    private String cameraNames;   //相机名称
    private String lensNames;   //镜头名称
    private String toolNames;   //工具名称

    public UserProfileBean() {

    }

    public UserProfileBean(UserInfo userInfo) {
        this.userName = userInfo.getUserName();
        this.avatarId = userInfo.getAvatarId();
        this.birthday = userInfo.getBirthday();
        this.sex = userInfo.getSex();
        this.description = userInfo.getDescription();
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getCameraNames() {
        return cameraNames;
    }

    public void setCameraNames(String cameraNames) {
        this.cameraNames = cameraNames;
    }

    public String getLensNames() {
        return lensNames;
    }

    public void setLensNames(String lensNames) {
        this.lensNames = lensNames;
    }

    public String getToolNames() {
        return toolNames;
    }

    public void setToolNames(String toolNames) {
        this.toolNames = toolNames;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Integer> getHobby() {
        return hobby;
    }

    public void setHobby(List<Integer> hobby) {
        this.hobby = hobby;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
