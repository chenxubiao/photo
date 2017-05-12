package cn.chenxubiao.common.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chenxubiao on 16-9-29.
 */
public class StringUtil {
    private static Random RANDOM = new Random(System.currentTimeMillis());
    private static Pattern BLANK_PATTERN = Pattern.compile("^([\\s]*)$");

    public static boolean isBlank(String value) {
        return isEmpty(value) || BLANK_PATTERN.matcher(value).matches();
    }

    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    public static boolean isEmpty(String... values) {
        if (values != null && values.length > 0) {
            String[] var1 = values;
            int var2 = values.length;
            for (int var3 = 0; var3< var2; ++var3) {
                String value = var1[var3];
                if (value == null || value.length() == 0) {
                    return true;
                }
            }
            return false;
        }else {
            return true;
        }
    }

    public static boolean isNotEmpty(String... values) {
        if (values != null && values.length > 0) {
            String[] var1 = values;
            int var2 = values.length;
            for (int var3 = 0; var3 < var2; ++var3) {
                String value = var1[var3];
                if (value == null || value.length() == 0) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }


    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 包括空格判断
     * @param input
     * @return
     */
    public static boolean isContainSpace(String input){
        return Pattern.compile("\\s+").matcher(input).find();
    }

    /**
     * 随机数
     * @param length
     * @param options
     * @return
     */
    public static String random(int length, char[] options) {
        if (length <= 1) {
            return "";
        } else {
            int var5;
            if (ArrayUtil.isEmpty(options)) {
                options = ConstArrays.BASE64;
                var5 = 62;
            } else {
                var5 = options.length;
            }
            char[] buff = new char[length];

            for (int i = 0; i< length; ++i) {
                buff[i] = options[RANDOM.nextInt(var5)];
            }

            return new String(buff);
        }
    }

    public static void main(String[] args) {

        random(1, null);
    }


    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneNumber(String str) {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    private static boolean isChinaPhoneLegal(String str) {
        if (isBlank(str)) {
            return false;
        }
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    private static boolean isHKPhoneLegal(String str) {
        if (isBlank(str)) {
            return false;
        }
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isEmail(String value) {
        if (isBlank(value)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        return pattern.matcher(value).matches();
    }

    public static boolean isDateType(String value) {
        if (isBlank(value)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        return pattern.matcher(value).matches();
    }

    public static boolean isIPAddress(String value) {
        if (isBlank(value)) {
            return false;
        }
        Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        return pattern.matcher(value).matches();
    }

}

