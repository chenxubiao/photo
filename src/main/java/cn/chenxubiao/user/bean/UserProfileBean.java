package cn.chenxubiao.user.bean;

/**
 * Created by chenxb on 17-4-14.
 */
public class UserProfileBean {
    private int avatarId;   //头像id
    private int backgroundId;  //用户喜好，标签id，以 , 隔开
    private String cellphone; //被关注者id，以 , 隔开
    private int sex;        //性别
    private String birthday;//出生日期
    private String description;
    private String categoryIds;
//    private int type;       //类型
    private String cameraNames;   //相机名称
    private String lensNames;   //镜头名称
    private String toolNames;   //工具名称

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
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
}
