package cn.chenxubiao.user.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.GuestBaseController;
import cn.chenxubiao.picture.service.PictureExifService;
import cn.chenxubiao.project.service.ProjectInfoService;
import cn.chenxubiao.project.service.ProjectTagService;
import cn.chenxubiao.user.bean.UserInfoBean;
import cn.chenxubiao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by chenxb on 17-5-11.
 */
@RestController
public class SearchController extends GuestBaseController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private PictureExifService pictureExifService;
    @Autowired
    private ProjectTagService projectTagService;
    @Autowired
    private ProjectInfoService projectInfoService;


    @RequestMapping(value = "/search/data", method = RequestMethod.GET)
    public ResponseEntity search(@RequestParam(value = "name", defaultValue = "") String name,
                                 HttpServletRequest request) {

        if (StringUtil.isBlank(name)) {
            return ResponseEntity.failure(Errors.PARAMETER_ILLEGAL);
        }
        name = "%" + name.trim() + "%";
        //user
        UserSession userSession = super.getUserSession(request);
        int userId = userSession == null ? 0 : userSession.getUserId();
        List<UserInfoBean> userInfoBeanList = userInfoService.search(name, userId);

        //project
        projectInfoService.search(name, userId);
        return ResponseEntity.success().set("user", userInfoBeanList);
    }
}
