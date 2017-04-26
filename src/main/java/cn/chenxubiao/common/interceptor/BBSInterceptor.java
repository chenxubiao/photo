package cn.chenxubiao.common.interceptor;


import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.web.RootController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenxb on 17-3-6.
 */

public class BBSInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            handler = ((HandlerMethod) handler).getBean();
        }
        if (handler != null && handler instanceof RootController) {
            RootController controller = (RootController) handler;
            if (controller.enableUserSession()) {
                if (controller.checkLogin()) {
                    UserSession userSession = controller.getUserSession(request);

                    if (userSession == null || !userSession.isLogin()) {
                        if(!"head".equalsIgnoreCase(request.getMethod())){
                            response.sendRedirect("/login");
                        }
                        return false;
                    }
                    if (!userSession.isAdmin() && userSession.getRoleSet() == null) {
                        controller.redirect403(response);
                        return false;
                    }
                    if (userSession.isLocking() || userSession.isClose()) {
                        controller.redirect403(response);
                        return false;
                    }
                    if (controller.checkAdmin()) {
                        if (!userSession.isAdmin()) {
                            controller.redirect403(response);
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
}
