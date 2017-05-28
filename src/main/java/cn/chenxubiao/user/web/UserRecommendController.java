package cn.chenxubiao.user.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.project.service.ProjectInfoService;
import cn.chenxubiao.project.service.ProjectLikeService;
import cn.chenxubiao.project.service.ProjectViewService;
import cn.chenxubiao.user.bean.UserInfoBean;
import cn.chenxubiao.user.domain.UserFollow;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.service.UserFollowService;
import cn.chenxubiao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


    @RequestMapping(value = "/recommend/user/list", method = RequestMethod.GET)
    public ResponseEntity recommendUser(HttpServletRequest request) {

        Pageable pageable = new PageRequest(0, 10);
        UserSession userSession = super.getUserSession(request);
        int userId = userSession.getUserId();

//        List<UserInfo> userInfos = userInfoService.findPopular(userId);

        List<UserFollow> userFollowing = userFollowService.findFollowing(userId);
        Set<Integer> userIds = new HashSet<>();
        // TODO: 17-5-28 用户推荐
        if (CollectionUtil.isEmpty(userFollowing)) {

        }else {
            for (UserFollow userFollow : userFollowing) {
                userIds.add(userFollow.getStartUserId());
            }
        }
        userIds.add(userId);
        List<UserInfo> userInfos = userInfoService.findIdNotIn(new ArrayList<>(userIds));
        if (CollectionUtil.isEmpty(userInfos)) {
            return ResponseEntity.failure(Errors.NOT_FOUND);
        }
        List<UserInfoBean> userInfoBeanList = new ArrayList<>();
        for (UserInfo userInfo : userInfos) {
            if (CollectionUtil.isNotEmpty(userIds) && userIds.contains(userInfo.getId())) {
                continue;
            }
            UserInfoBean userInfoBean = new UserInfoBean(userInfo);
            userInfoBeanList.add(userInfoBean);
        }
        return ResponseEntity.success().set(BBSConsts.DATA, userInfoBeanList);
    }
}
