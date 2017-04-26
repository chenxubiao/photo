package cn.chenxubiao.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by chenxubiao on 17-3-7.
 */
public class CollectionUtil {

    public CollectionUtil() {
    }

    public static boolean isEmpty(Collection<? extends Object> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<? extends Object> collection) {
        return collection != null && collection.size() > 0;
    }

    public static boolean isEmpty(Map<? extends Object, ? extends Object> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<? extends Object, ? extends Object> map) {
        return map != null && map.size() > 0;
    }
}
