package cn.chenxubiao.user.bean;

/**
 * Created by chenxb on 17-4-14.
 */
public class UserProfileBean {
    private int avatarId;   //头像id
    private String tagIds;  //用户喜好，标签id，以 , 隔开
    private String userIds; //被关注者id，以 , 隔开
    private int sex;        //性别
    private String birthday;//出生日期

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
