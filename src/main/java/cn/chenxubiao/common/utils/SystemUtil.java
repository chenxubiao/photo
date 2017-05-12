package cn.chenxubiao.common.utils;

/**
 * Created by chenxb on 17-5-11.
 */
public class SystemUtil {
    private static String os = System.getProperty("os.name").toLowerCase();

    public static boolean isWindowsOS() {

        if ("windows".equalsIgnoreCase(os)) {
            return true;
        }
        return false;
    }

    public static boolean isLinuxOS() {
        if ("linux".equalsIgnoreCase(os)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {

    }
}
