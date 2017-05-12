package cn.chenxubiao.project.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.web.GuestBaseController;
import cn.chenxubiao.picture.bean.PicInfoBean;
import cn.chenxubiao.project.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by chenxb on 17-5-12.
 */
@RestController
public class ProjectIndexPageController extends GuestBaseController {
    @Autowired
    private ProjectInfoService projectInfoService;

    @RequestMapping(value = "/index/picture/data", method = RequestMethod.GET)
    public ResponseEntity getIndexPicInfo(HttpServletRequest request,
                                          Pageable pageable) {

        UserSession userSession = getUserSession(request);
        List<PicInfoBean> picInfoBeanList;
        if (userSession == null) {
            picInfoBeanList = projectInfoService.findPicInfoByPage(pageable);
        } else {
            picInfoBeanList = projectInfoService.findPicInfoByPage(pageable, userSession.getUserId());
        }
        return ResponseEntity.success().set(BBSConsts.DATA, picInfoBeanList);
    }
}
