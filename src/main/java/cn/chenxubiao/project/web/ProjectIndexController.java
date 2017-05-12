package cn.chenxubiao.project.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.project.service.ProjectInfoService;
import cn.chenxubiao.user.domain.UserFollow;
import cn.chenxubiao.user.service.UserFollowService;
import cn.chenxubiao.user.web.UserHomeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxb on 17-4-28.
 */
@RestController
public class ProjectIndexController extends CommonController {
    @Autowired
    private UserFollowService userFollowService;
    @Autowired
    private ProjectInfoService projectInfoService;
    @Autowired
    private UserHomeController userHomeController;

    public ResponseEntity findFollowingProject(Pageable pageable, HttpServletRequest request) {

        UserSession userSession = super.getUserSession(request);
        int userId = userSession.getUserId();
        List<UserFollow> userFollowList = userFollowService.findFollowing(userId);
        if (CollectionUtil.isEmpty(userFollowList)) {
            return ResponseEntity.failure(Errors.NONE_FOLLOWING);
        }
        List<Integer> following = new ArrayList<>();
        for (UserFollow userFollow : userFollowList) {
            following.add(userFollow.getStartUserId());
        }
        Page<ProjectInfo> projectInfoPage = projectInfoService.findInUserIdAndPage(following, pageable);
        if (CollectionUtil.isEmpty(projectInfoPage.getContent())) {
            return ResponseEntity.failure(Errors.FOLLOWING_NOT_UPLOAD_PROJECT);
        }

        //todo
        return null;
    }

    @RequestMapping(value = "/project/pic/list", method = RequestMethod.GET)
    public ResponseEntity findProjectList(@RequestParam(value = "userId", defaultValue = "0") int userId,
                                          HttpServletRequest request, Pageable pageable) {

        return userHomeController.getUserHomeData(userId, request, pageable);
    }
}
