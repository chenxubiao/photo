package cn.chenxubiao.common.interceptor;

import cn.chenxubiao.common.annotation.Authority;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.NumberUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by chenxb on 17-3-4.
 */
@Component
public class AuthorityInterceptor extends HandlerInterceptorAdapter {

    @Override
    public final boolean preHandle(HttpServletRequest request,
                                   HttpServletResponse response, Object handler) throws Exception{

        Authority annotation = null;
        if(handler instanceof HandlerMethod){
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Authority.class);
        }else{
            return true;
        }
        if (annotation != null) {
            String privilege = annotation.privilege();
            Set<Integer> privilegeSet = null;
            if (privilege.length() > 0) {
                privilegeSet = NumberUtil.parseToIntSet(privilege);
            }

            if (privilegeSet != null && privilegeSet.size() >0 && annotation.checkAuth()) {
                UserSession session =  (UserSession)request.getAttribute(BBSConsts.USER_SESSION_KEY);
                if(session.isAdmin()){
                    return true;
                }
                Set<Integer> roleSet = session.getRoleSet();
                if (roleSet != null && roleSet.size() > 0) {
                    for (int id : privilegeSet) {
                        if (roleSet.contains(id)) {
                            return true;
                        }
                    }
                }
                response.sendRedirect("/error/403");
                return false;
            }
        }
        return true;
    }
}
