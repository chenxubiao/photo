package cn.chenxubiao.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by chenxb on 17-4-14.
 */
public class TimeUtil {

    public static int DATE_TYPE_BEFORE = 0;
    public static int DATE_TYPE_AFTER = 1;

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

    public static Date parse(String dateString,String format) {
        if(dateString != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
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

    public static Date getDayBefore(Date date, int day) {
        return disposeDate(date, day, 0);
    }

    public static Date getDayBefore(int day) {
        return disposeDate(new Date(), day, 0);
    }
    public static Date getDayBefore() {
        return disposeDate(new Date(), 1, 0);
    }

    /**
     * @param type >0 几天后的时间，else 几天前
     * @param date
     * @param day
     * @return
     */
    public static Date disposeDate(Date date, int day, int type) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int DATE = c.get(Calendar.DATE);

        //几天后
        if (type == DATE_TYPE_AFTER) {
            c.set(Calendar.DATE, DATE + day);
        } else {
            //几天前
            c.set(Calendar.DATE, DATE - day);
        }
        return c.getTime();
    }

    public static Date getTodayBegin() {
        Date date = new Date();
        String dateString = format(date, "yyyy-MM-dd 00:00:00");
        System.out.println(dateString);
        return parse(dateString);
    }
}
