package cn.chenxubiao.user.bean;

import cn.chenxubiao.common.utils.ConstStrings;
import cn.chenxubiao.common.utils.consts.Errors;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by chenxb on 17-3-7.
 */
public class LoginBean {
    @Size(min = 1, max = 32, message = Errors.USER_USERNAME_NULL)
    private String userName;
    @Pattern(regexp = ConstStrings.REGX_CELLPHONE, message = Errors.CELLPHONE_NULL_ERROR)
    private String cellphone;
    @Pattern(regexp = ConstStrings.REGX_EMAIL, message = Errors.NOT_EMAIL_ERROR)
    private String email;
    @Size(min = 1, max = 32, message = Errors.LOGIN_PASSWORD_NULL_ERROR)
    private String password;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
