package cn.chenxubiao.user.domain;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * Created by chenxb on 17-3-4.
 */
@Entity
@Table(name = "bbs_user_info")
public class UserInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(min = 1, max = 32, message = "用户名不能为空")
    private String userName;
    private int avatarId;   //头像id
    private int backgroundId;
    private String cellphone;
    private int sex;    //性别
    private int status;
    private String password;
    private String description = "";
    private int userRole;
    @Pattern(regexp = "^[a-zA-Z0-9_\\+\\-\\.]+(\\.[a-zA-Z0-9_\\+\\-]+)*@[a-zA-Z0-9]+(\\.?[a-zA-Z0-9\\-]+)*\\.([a-zA-Z]{2,4})$", message = "请输入正确邮件格式（邮件格式：myname@example.com）")
    private String email;
    private Date birthday;
    @Column(name = "createTime", updatable = false)
    private Date createTime;
    @Column(name = "modifyTime")
    private Date modifyTime;
    @Transient
    private List<UserRole> userRoleList;

    public Integer getId() {
        return id;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }
}
