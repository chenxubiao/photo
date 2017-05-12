package cn.chenxubiao.user.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.web.GuestBaseController;
import cn.chenxubiao.picture.bean.PicInfoBean;
import cn.chenxubiao.project.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController extends GuestBaseController {
    @Autowired
    private ProjectInfoService projectInfoService;

    @RequestMapping(value = {"index", "/"}, method = RequestMethod.GET)
    public String getIndexPage(Map<String, Object> map) {
        map.put("hello", "bbs");
        return "/index";
    }
}
