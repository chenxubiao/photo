package cn.chenxubiao.common.utils.consts;

/**
 * Created by chenxb on 17-3-4.
 */
public class BBSConsts {

    public static final String REDIS_PROJECT_PREFIX = "bbs_";
    public static final String REDIS_STAT_TOTAL_KEY = REDIS_PROJECT_PREFIX + "stat_total";
    public static final String REDIS_TIMESTAMP_KEY = REDIS_PROJECT_PREFIX + "timestamp";
    public static final String PICTURE_PREFIX = "photo@";
    public static final String DATA = "data";
    public static final String UPLOAD_NAME = "uploadFile";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String USER_SESSION_KEY = "thisUser";
    public static final int PICTURE_UPLOAD_NAME_LEN = 128;
    public static final String PROTECTED_BASE_PATH = "/var/upload/bbs/pictures";
    public static final String PROTECTED_PIC_DISPOSE_PTTH = "/var/upload/bbs/pics";
    public static final long PICTURE_UPLOAD_MAX_SIZE = 3145728L;    //3M
    public static final String BBS_NAME = "图片社区";
    public static final String PAGINATION = "pagination";

    public static final String FOLLOW = "关注了您";
    public static final String UNFOLLOW = "取消关注您";

    public static final String LIKED = "喜欢了您的图片";
    public static final String UNLIKE = "取消喜欢您的图片";

    public static final int CRM_NORMAL = 0;
    public static final int CRM_ADMIN = 1;

    public static final class UserSelf {
        public static final int NOT_SELF = 0;
        public static final int SELF = 1;
    }

    public static final class UserFollow {
        public static final int NOT_FOLLOW = 0;
        public static final int FOLLOW = 1;
    }

    public static final class UserRole {
        public static final int USER_IS_COMMON = 1;
        public static final int USER_IS_OPERATOR = 2;
    }

    public static final class UserStatus{
        public static final int USER_IS_LOCKING = 0;
        public static final int USER_IS_NORMAL = 1;
        public static final int USER_IS_CLOSE = 2;
    }

    public static final class UserSex{
        public static final int SEX_UNKNOWN = 0;
        public static final int SEX_MALE = 1;
        public static final int SEX_FEMALE = 2;
    }

    public static final class UserToolType {
        public static final int CAMERA = 1;
        public static final int LEN = 2;
        public static final int TOOL = 3;
    }

//    public static final class AccountLogType {
//
//        public static final int ADD_REGESTER = 1;   //注册奖励
//        public static final int ADD_PAY_SUCCESS = 2;//充值成功
//        public static final int DEL_PAY_FAILURE = 3;//充值失败
//        public static final int ADD_LOGIN = 4;      //连续登录
//        public static final int DEL_UPLOAD = 5;     //上传花费积分
//        public static final int ADD_DOWNLOAD = 6;   //被别人下载获得
//        public static final int DEL_DOWNLOAD = 7;   //下载扣除
//    }

    public static final class MessageStatus{
        public static final int SEND = 1;   //1：已发送，待查看，2：已查看
        public static final int VIEWD = 2;
    }

    public static final class MessageType {
        public static final int PROJECT_LIKE = 1;
        public static final int USER_FOLLOW = 2;
        public static final int USER_REGESTER = 3;
        public static final int ACCOUNT_CHANGE = 4;
        public static final int LOGIN_ALWOYS = 5;
    }

    public static final class ProjectAuth {
        public static final int NONE_AUTH = 0;
        public static final int AUTH = 1;
    }

}
