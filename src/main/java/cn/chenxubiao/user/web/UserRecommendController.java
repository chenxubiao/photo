package cn.chenxubiao.user.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.project.domain.ProjectLike;
import cn.chenxubiao.project.service.ProjectInfoService;
import cn.chenxubiao.project.service.ProjectLikeService;
import cn.chenxubiao.project.service.ProjectViewService;
import cn.chenxubiao.user.domain.UserFollow;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.service.UserFollowService;
import cn.chenxubiao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by chenxb on 17-5-14.
 */
@RestController
public class UserRecommendController extends CommonController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserFollowService userFollowService;
    @Autowired
    private ProjectInfoService projectInfoService;
    @Autowired
    private ProjectLikeService projectLikeService;
    @Autowired
    private ProjectViewService projectViewService;


    public ResponseEntity recommendUser(HttpServletRequest request) {

        Pageable pageable = new PageRequest(0, 10);
        UserSession userSession = super.getUserSession(request);
        int userId = userSession.getUserId();
        List<UserFollow> userFollowing = userFollowService.findFollowing(userId);
        //推荐热门用户
        if (CollectionUtil.isEmpty(userFollowing)) {
            List<ProjectLike> projectLikeList = projectLikeService.findPopular(pageable);
        }
        //todo
        return null;
    }
}
