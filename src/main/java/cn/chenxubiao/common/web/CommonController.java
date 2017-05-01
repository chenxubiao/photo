package cn.chenxubiao.common.web;

/**
 * Created by chenxb on 17-4-2.
 */
public class CommonController extends BBSBaseController {
    @Override
    public boolean checkLogin() {
        return true;
    }

    @Override
    public boolean enableUserSession() {
        return true;
    }
}
