package cn.chenxubiao.common.utils;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by chenxb on 17-4-2.
 */
public class NumberUtil {
    private static Random RANDOM = new Random(System.currentTimeMillis());

    public static int nextInt(int n) {
        return RANDOM.nextInt(n);
    }

    public static final int random(int length) {
        if (length > 0 && length < 10) {
            int sum = 0;
            int n = 1;
            int r;
            for (int i = 1; i < length; ++i) {
                r = nextInt(10);
                sum += r * n;
                n *= 10;
            }

            r = 1 + nextInt(9);
            sum += r * n;
            return sum;
        } else {
            return 0;
        }
    }

    public static Set<Integer> parseToIntSet(String ids) {
        if (StringUtil.isBlank(ids)) {
            return null;
        }
        Set<Integer> set = new LinkedHashSet<>();
        String[] strings = ids.split(",");
        for (String string : strings) {
            set.add(Integer.parseInt(string));
        }
        return set;
    }

    public static boolean is(String value) {
        if(value != null && value.length() > 0) {
            char[] chars = value.toCharArray();

            for(int i = 0; i < chars.length; ++i) {
                if(!Character.isDigit(chars[i])) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static int parseIntQuietly(Object value) {
        return parseIntQuietly(value, 0);
    }

    public static int parseIntQuietly(Object value, int def) {
        if(value != null) {
            if(value instanceof Number) {
                return ((Number)value).intValue();
            }

            try {
                return Integer.valueOf(value.toString()).intValue();
            } catch (Throwable var3) {
                ;
            }
        }

        return def;
    }
}
