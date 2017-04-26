package cn.chenxubiao.user.bean;

/**
 * Created by chenxb on 17-4-14.
 */
public class RegisterBean {
    private String email;       //电子邮件
    private String userName;    //用户名
    private String password;    //密码
    private String code;    //验证码

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
