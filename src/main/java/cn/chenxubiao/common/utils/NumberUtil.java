package cn.chenxubiao.common.utils;

import java.util.HashSet;
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
}
