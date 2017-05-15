package cn.chenxubiao.test;

import cn.chenxubiao.common.utils.TimeUtil;

import java.util.Date;

/**
 * Created by chenxb on 17-5-15.
 */
public class Hello {
    public static void main(String[] args) {
        String dateString = "2017:05:06 16:45:20";
        Date date = TimeUtil.parse(dateString, "yyyy:MM:dd HH:mm:ss");
        System.out.println(date);
    }
}
