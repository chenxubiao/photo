package cn.chenxubiao.common.filter;

import cn.chenxubiao.common.utils.WebUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by chenxb on 17-2-27.
 */
@WebFilter(filterName = "basicFilter", urlPatterns = "/*")
public class BasicFilter implements Filter {
    public static final String HOST_suffix = ".chenxubiao.cn";
    public static final String HOST = "bbs" + HOST_suffix;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        //这里填写你允许进行跨域的主机ip
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        //允许的访问方法
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
//        //Access-Control-Max-Age 用于 CORS 相关配置的缓存
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        setGloableAttributes(request, response);
        filterChain.doFilter(request, response);
    }

    public void destroy() {

    }

    private final void setGloableAttributes(HttpServletRequest request, HttpServletResponse response) {

        if (request.getAttribute(BBSConsts.USER_SESSION_KEY) == null) {
            request.setAttribute("BBSConsts.USER_SESSION_KEY", null);
        }
        request.setAttribute("thisHost", HOST);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://");
        stringBuilder.append(HOST);

        stringBuilder.append(request.getRequestURI());
        String query = request.getQueryString();
        if (query != null && query.length() > 0) {
            stringBuilder.append("?");
            query = WebUtil.unescapeHtml(query);
            stringBuilder.append(query);
        }
        String url = null;
        try {
            url = URLEncoder.encode(stringBuilder.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        request.setAttribute("thisUrl", url);
        request.setAttribute("thisUri", request.getRequestURI());
    }
}
