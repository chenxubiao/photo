package cn.chenxubiao.common.utils;

import java.util.Random;
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

}

