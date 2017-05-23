package cn.chenxubiao.message.enums;

/**
 * Created by chenxb on 17-5-16.
 */
public enum MessageTypeEnum {

    REGEISTER(1, "注册"),
    LOGIN(2, "连续登录"),
    ACCOUNT_CHANGE(3, "账户变动"),
    PROJECT_LIKE(4, "喜欢了您的图片"),
    PROJECT_UNLIKE(5, "取消喜欢了您的图片"),
    USER_FOLLOW(6, "关注了您"),
    USER_UNFOLLOW(7, "取消关注了您"),;


    private int code;
    private String value;

    MessageTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getValue(int code) {
        for (MessageTypeEnum typeEnum : MessageTypeEnum.values()) {
            if (typeEnum.getCode() == code) {
                return typeEnum.getValue();
            }
        }
        return "未知";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
