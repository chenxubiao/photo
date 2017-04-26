package cn.chenxubiao.common.utils;

import java.util.Random;

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
}
