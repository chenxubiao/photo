package cn.chenxubiao.common.utils;

import java.io.File;

/**
 * Created by chenxb on 17-4-2.
 */
public class FileUtil {
    public static String getExtension(String name) {
        return getExtension(name, "");
    }

    public static String getExtension(String name, String defaultString) {
        if(StringUtil.isEmpty(name)) {
            return defaultString;
        } else {
            int i = name.lastIndexOf(46);
            return i >= 0?name.substring(i + 1).toLowerCase():defaultString;
        }
    }

    public static void createDirs(File file) {
        if(file != null) {
            File path = file.isDirectory()?file:file.getParentFile();
            if(!path.exists() && !path.mkdirs()) {
                throw new RuntimeException("mkdirs failed");
            }
        }
    }
}
