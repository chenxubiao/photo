package cn.chenxubiao.common.web;


import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.domain.UserRole;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chenxb on 17-3-5.
 */
public class RootController {

    @ExceptionHandler(Throwable.class)
    public String handleException(Throwable e, HttpServletRequest request, HttpServletResponse response) {
        if (e != null) {
            e.printStackTrace();
        }
        redirect500(response);
        return null;
    }

    public boolean checkAdmin() {
        return false;
    }

    public void redirect(HttpServletResponse response, String url) {
        try {
            if (url == null || url.length() == 0) {
                url = "/";
            }
            response.sendRedirect(url);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void redirect403(HttpServletResponse response) {
        try {
            response.sendRedirect("/error/403");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void redirect404(HttpServletResponse response) {
        try {
            response.sendRedirect("/error/404");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void redirect500(HttpServletResponse response) {
        try {
            response.sendRedirect("/error/500");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public UserSession buildUserSession(UserInfo userInfo) {
        if (userInfo != null) {
            UserSession userSession = new UserSession(userInfo);
            List<UserRole> roles = userInfo.getUserRoleList();
            if (CollectionUtil.isNotEmpty(roles)) {
                Set<Integer> roleSet = new HashSet<>();
                for (UserRole role : roles) {
                    roleSet.add(role.getRoleId());
                }
                userSession.setRoleSet(roleSet);
            }
            return userSession;
        }
        return null;
    }


    public void setUserSession(HttpServletRequest request, UserInfo userInfo) {
        if (request != null && userInfo != null) {
            UserSession userSession = buildUserSession(userInfo);
            request.getSession().setAttribute(BBSConsts.USER_SESSION_KEY, userSession);
        }
    }

    public void setUserSession(HttpServletRequest request, UserSession userSession) {
        if (request != null && userSession != null) {
            request.getSession().setAttribute(BBSConsts.USER_SESSION_KEY, userSession);
        }
    }

    public void deleteUserSession(HttpServletRequest request) {
        request.getSession().setAttribute(BBSConsts.USER_SESSION_KEY, null);
    }

    public boolean checkLogin() {
        return false;
    }
    public boolean enableUserSession() {
        return false;
    }

    public UserSession getUserSession(HttpServletRequest request) {
        if(request != null) {
            UserSession userSession = (UserSession) request.getSession().getAttribute(BBSConsts.USER_SESSION_KEY);
            if(userSession != null) {
                return userSession;
            }
        }
        return null;
    }
}
