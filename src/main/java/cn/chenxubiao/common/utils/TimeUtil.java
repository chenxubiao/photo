package cn.chenxubiao.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenxb on 17-4-14.
 */
public class TimeUtil {
    public static String format(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(Date date, String formatString) {
        if(date == null) {
            date = new Date();
        }

        return (new SimpleDateFormat(formatString)).format(date);
    }

    public static Date parse(String dateString) {
        if(dateString != null) {
            SimpleDateFormat dateFormat = getDateFormatByDateString(dateString);
            if(dateFormat != null) {
                try {
                    return dateFormat.parse(dateString);
                } catch (ParseException var3) {
                    ;
                }
            }
        }

        return null;
    }

    public static SimpleDateFormat getDateFormatByDateString(String dateString) {
        if(dateString != null) {
            if(dateString.length() == "yyyyMMdd".length()) {
                return new SimpleDateFormat("yyyyMMdd");
            }

            if(dateString.length() == "yyyy-MM-dd HH:mm:ss".length()) {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }

            if(dateString.length() < "yyyy-MM-dd HH:mm:ss".length()) {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss".substring(0, dateString.length()));
            }
        }

        return null;
    }

}
