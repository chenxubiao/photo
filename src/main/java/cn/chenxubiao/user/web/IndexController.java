package cn.chenxubiao.user.web;

import cn.chenxubiao.common.web.GuestBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Map;

@Controller
public class IndexController extends GuestBaseController {

    @RequestMapping(value = {"index", "/"}, method = RequestMethod.GET)
    public String getIndexPage(Map<String, Object> map) {
        map.put("hello", "bbs");
        return "/index";
    }
}
