package cn.chenxubiao.common.utils.consts;

/**
 * Created by chenxb on 17-3-4.
 */
public class BBSConsts {

    public static final String REDIS_PROJECT_PREFIX = "bbs_";
    public static final String REDIS_STAT_TOTAL_KEY = REDIS_PROJECT_PREFIX + "stat_total";
    public static final String REDIS_TIMESTAMP_KEY = REDIS_PROJECT_PREFIX + "timestamp";
    public static final String UPLOAD_NAME = "uploadFile";
    public static final String USER_SESSION_KEY = "thisUser";
    public static final int PICTURE_UPLOAD_NAME_LEN = 128;
    public static final String PROTECTED_BASE_PATH = "/var/upload/bbs/pictures";
    public static final long PICTURE_UPLOAD_MAX_SIZE = 3145728L;    //3M

    public static final int CRM_NORMAL = 0;
    public static final int CRM_ADMIN = 1;

    public static final class UserRole {
        public static final int USER_IS_GUEST = 1;
        public static final int USER_IS_SIGN = 2;
        public static final int USER_IS_OPERATOR = 3;
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

    public static final class UserRegesterType {
        public static final int UNKNOWN = 0;
        public static final int EMAIL = 1;
        public static final int CELLPHONE = 2;
    }

    public static final class PictureBanner {
        public static final int FIRST_BANNER = 1;
        public static final int SECOND_BANNER = 2;
        public static final int THIRD_BANNER = 3;
        public static final int FOURTH_BANNER = 4;
        public static final int FIFTH_BANNER = 5;
        public static final int SIXTH_BANNER = 6;
        public static final int SEVENTH_BANNER = 7;
        public static final int EIGHTH_BANNER = 8;
        public static final int NINTH_BANNER = 9;
    }

    public static final class TagInfoStatus{
        public static final int CLOSE = 0;  //删除
        public static final int NORMAL = 1; //正常
    }
}
