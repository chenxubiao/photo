package cn.chenxubiao.user.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.project.bean.ProjectBean;
import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.project.service.ProjectInfoService;
import cn.chenxubiao.user.bean.UserHomeBean;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.service.UserFollowService;
import cn.chenxubiao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxb on 17-5-1.
 */
@RestController
public class UserHomeController extends CommonController {
    @Autowired
    private ProjectInfoService projectInfoService;
    @Autowired
    private UserFollowService userFollowService;
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/user/home/data", method = RequestMethod.GET)
    public ResponseEntity getUserHomeData(@RequestParam(value = "userId", defaultValue = "0") int userId,
                                          HttpServletRequest request, Pageable pageable) {
        UserInfo userInfo;
        if (userId > 0) {
            userInfo = userInfoService.findById(userId);
        }else {
            UserSession userSession = getUserSession(request);
            userInfo = userInfoService.findById(userSession.getUserId());
        }
        if (userInfo == null) {
            return ResponseEntity.failure(Errors.USER_INFO_NOT_FOUND);
        }
        userId = userInfo.getId();
        int followers = userFollowService.findFollowsCount(userId);
        int following = userFollowService.findFollowingCount(userId);

        List<ProjectInfo> projectInfoList = projectInfoService.findByUserAndPage(userId, pageable);
        UserHomeBean userHomeBean;
        if (projectInfoList == null) {
            userHomeBean = new UserHomeBean(following, followers, userInfo, null);
            return ResponseEntity.success().set(BBSConsts.DATA, userHomeBean);
        }
        List<ProjectBean> projectBeanList = new ArrayList<>();
        for (ProjectInfo projectInfo : projectInfoList) {
            if (projectInfo == null) {
                continue;
            }
            ProjectBean projectBean = new ProjectBean(projectInfo);
            projectBeanList.add(projectBean);
        }
        userHomeBean = new UserHomeBean(following, followers, userInfo, projectBeanList);
        return ResponseEntity.success().set(BBSConsts.DATA, userHomeBean);
    }
}
