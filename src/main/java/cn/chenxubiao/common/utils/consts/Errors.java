package cn.chenxubiao.common.utils.consts;

/**
 * Created by chenxb on 17-3-6.
 */
public class Errors {
    public static final String JSON_NOT_LOGIN = "{ \"success\": false, \"message\": \"404 Not Found !\", \"errorCode\": 1, \"vars\": null }";
    public static final String JSON_NOT_HAVE_ROLES = "{ \"success\": false, \"message\": \"403 Permission Denied !\", \"errorCode\": 2, \"vars\": null }";
    public static final String JSON_ACCOUNT_CLOSE = "{ \"success\": false, \"message\": \"403 Account Is Closed !\", \"errorCode\": 3, \"vars\": null }";
    public static final String JSON_NOT_ADMIN = "{ \"success\": false, \"message\": \"403 Permission Denied !\", \"errorCode\": 4, \"vars\": null }";

    public static final String UNKNOWN_ERROR = "未知错误";
    public static final String PARAMETER_ILLEGAL = "参数不合法";
    public static final String FILE_TYPE_ERROR = "类型不支持";
    public static final String NOT_EMAIL_ERROR = "请输入正确邮件格式（邮件格式：myname@example.com）";
    public static final String CELLPHONE_NULL_ERROR = "手机号格式不正确";
    public static final String KAPTCHA_ERROR = "验证码错误";

    public static final String UPLOAD_ERROR = "上传时发生错误";
    public static final String PICTURE_NOT_FOUND = "图片不存在";
    public static final String PICTURE_SIZE_ERROR = "图片质量过低";

    public static final String LOGIN_ERROR = "账号或密码错误";
    public static final String LOGIN_PASSWORD_NULL_ERROR = "密码不能为空";
    public static final String USER_IS_LOCKING = "账户未激活";
    public static final String ACCOUNT_NOT_FOUND = "未找到账户";
    public static final String PASSWORD_ERROR = "密码错误";
    public static final String PASSWORD_LENGTH_ERROR = "密码长度应在6～32之间";
    public static final String PASSWORD_OLD_ERROR = "旧密码错误";
    public static final String PASSWOED_OLD_EQUAL_NEW = "原密码与新密码相同";

    public static final String USER_INFO_NOT_FOUND = "用户不存在";
    public static final String USER_USERNAME_NULL = "用户名不能为空";
    public static final String USER_USERNAME_IS_CHINESE = "不允许出现中文字符、空格且长度位于2～32之间";
    public static final String USER_USERNAME_HAS_SPACE = "用户名不允许出现空格";
    public static final String NONE_FOLLOWS = "无粉丝信息";
    public static final String NONE_FOLLOWING = "无用户关注信息";
    public static final String NOT_FOUND = "数据库未找到记录";

    public static final String USER_USERNAME_IS_EXISTS = "用户名已被占用";
    public static final String EMAIL_IS_EXISTS = "邮箱已被注册";
    public static final String CELLPHONE_IS_EXISTS = "手机号已被注册";

    public static final String FILE_NAME_ERROR = "文件名为空";
    public static final String FILE_EXT_TYPE_ERROR="不支持文件扩展名: ";
    public static final String FILE_LENGTH_TOO_LOGN = "不支持大于3M文件上传";

    public static final String LOGIN_FIRST = "请先登录";
    public static final String EMAIL_NOT_FOUNT = "未找到注册邮箱";

    public static final String FOLLOWING_NOT_UPLOAD_PROJECT = "您关注的用户暂未分享图片";
    public static final String PROJECT_NOT_FOUND = "图片项目信息未找到";

    public static final String TAG_CATEGORY_NOT_FOUND = "分类不存在";
    public static final String TAG_NOT_FOUND = "标签不存在";

    public static final String MESSAGE_NONE_UNLOOK = "没有未查看的消息";
    public static final String MESSAGE_NONE_RECEIVE = "没有新消息";

    public static final String ACCOUNT_BALANCE = "账户余额不足";
    public static final String PUBLISH_MONEY_ERROR = "设置下载金额不合法";
}
