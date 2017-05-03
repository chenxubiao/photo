package cn.chenxubiao.common.interceptor;


import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.ConstStrings;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.RootController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by chenxb on 17-3-6.
 */

public class BBSInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            handler = ((HandlerMethod) handler).getBean();
        }
        if (handler != null && handler instanceof RootController) {
            RootController controller = (RootController) handler;
            if (controller.enableUserSession()) {
                if (controller.checkLogin()) {
                    UserSession userSession = (UserSession) request.getSession().getAttribute(BBSConsts.USER_SESSION_KEY);
                    if (userSession == null || !userSession.isLogin()) {
                        sendMessage(response, Errors.JSON_NOT_LOGIN);
                        return false;
                    }
                    if (!userSession.isAdmin() && userSession.getRoleSet() == null) {
                        sendMessage(response, Errors.JSON_NOT_HAVE_ROLES);
//                        controller.redirect403(response);
                        return false;
                    }
                    if (userSession.isLocking() || userSession.isClose()) {
                        sendMessage(response, Errors.JSON_ACCOUNT_CLOSE);
//                        controller.redirect403(response);
                        return false;
                    }
                    if (controller.checkAdmin()) {
                        if (!userSession.isAdmin()) {
                            sendMessage(response, Errors.JSON_NOT_ADMIN);
//                            controller.redirect403(response);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

    private void sendMessage(HttpServletResponse response, String message) {

        response.setContentType(ConstStrings.CONTENT_TYPE_JSON);
        response.setCharacterEncoding(ConstStrings.CHARACTER_ENCOING_UTF8);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
