package cn.chenxubiao.user.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.GuestBaseController;
import cn.chenxubiao.project.bean.PicInfo;
import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.project.service.ProjectInfoService;
import cn.chenxubiao.project.web.ProjectIndexController;
import cn.chenxubiao.user.bean.UserInfoBean;
import cn.chenxubiao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by chenxb on 17-5-11.
 */
@RestController
public class SearchController extends GuestBaseController {

    @Autowired
    private ProjectIndexController projectIndexController;
    @Autowired
    private ProjectInfoService projectInfoService;
    @Autowired
    private UserInfoService userInfoService;



    @RequestMapping(value = "/search/data")
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
        List<ProjectInfo> projectBeanList = projectInfoService.search(name, userId);
        Set<Integer> projectIdSet = null;
        Map<Integer, ProjectInfo> mapName = null;
        if (CollectionUtil.isNotEmpty(projectBeanList)) {
            projectIdSet = new HashSet<>();
            mapName = new HashMap<>();
            for (ProjectInfo projectBean : projectBeanList) {
                projectIdSet.add(projectBean.getId());
                mapName.put(projectBean.getId(), projectBean);
            }
        }

        if (CollectionUtil.isEmpty(projectIdSet)) {
            projectBeanList = projectInfoService.searchByTag(name);
        }else {
            List<ProjectInfo> projectBeans = projectInfoService.searchByTag(name);
            if (CollectionUtil.isNotEmpty(projectBeans)) {
                Map<Integer, ProjectInfo> mapTag = new HashMap<>();
                for (ProjectInfo projectBean : projectBeans) {
                    mapTag.put(projectBean.getId(), projectBean);
                }
                mapName.putAll(mapTag);
            }
            List<Integer> keys = new ArrayList<>(mapName.keySet());
            Collections.sort(keys);
            projectBeanList = new ArrayList<>();
            for (int id : keys) {
                projectBeanList.add(mapName.get(id));
            }
        }

        List<PicInfo> picInfoList = projectIndexController.getPicInfoList(projectBeanList, userId);
        return ResponseEntity.success().set("user", userInfoBeanList).set(BBSConsts.DATA, picInfoList);
    }
}
