package cn.chenxubiao.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by chenxb on 17-3-6.
 */
@Controller
public class ErrorController extends RootController {
    @RequestMapping("/error/{code}")
    public String index(HttpServletRequest request, HttpServletResponse response, @PathVariable String code, Map map) {
        if("403".equals(code)){
            map.put("message", "403 Permission Denied !");
        }else if("404".equals(code)){
            map.put("message", "404 Not Found !");
        }else if("500".equals(code)){
            map.put("message", "500 Internal Server Error !");
        }else{
            map.put("message", "Unkown Error!");
        }
        return "/error";
    }

    @Override
    public boolean checkLogin() {
        return false;
    }
}
