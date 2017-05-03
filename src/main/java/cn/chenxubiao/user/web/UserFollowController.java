package cn.chenxubiao.user.web;

import cn.chenxubiao.common.bean.CommonBean;
import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.user.bean.UserInfoBean;
import cn.chenxubiao.user.domain.UserFollow;
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
public class UserFollowController extends CommonController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserFollowService userFollowService;

    @RequestMapping(value = "/user/follow/update", method = RequestMethod.POST)
    public ResponseEntity userFollow(@RequestParam(value = "userId", defaultValue = "0") int userId,
                                     HttpServletRequest request) {

        if (userId <= 0) {
            return ResponseEntity.failure(Errors.PARAMETER_ILLEGAL);
        }
        UserSession userSession = getUserSession(request);
        int endUserId = userSession.getUserId();
        UserInfo userInfo = userInfoService.findById(userId);
        if (userInfo == null) {
            return ResponseEntity.failure(Errors.USER_INFO_NOT_FOUND);
        }
        int startUserId = userInfo.getId();
        userFollowService.disposeFollowUser(startUserId, endUserId);
        return ResponseEntity.success();
    }

    /**
     * 显示关注和被关注用户
     * @param request
     * @param userId
     * @param type  0:求关注者  1:求被关注者
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/user/follow/show", method = RequestMethod.GET)
    public ResponseEntity showFollow(HttpServletRequest request,
                                     @RequestParam(value = "userId", defaultValue = "0") int userId,
                                     @RequestParam(value = "type",defaultValue = "0") int type,
                                     Pageable pageable) {

        UserInfo userInfo;
        if (userId > 0) {
            userInfo = userInfoService.findById(userId);
        } else {
            UserSession userSession = getUserSession(request);
            userInfo = userInfoService.findById(userSession.getUserId());
        }
        if (userInfo == null) {
            return ResponseEntity.failure(Errors.USER_INFO_NOT_FOUND);
        }
        List<UserInfoBean> userInfoBeanList;
        if (type > 0) {
            //显示此人关注的用户
            List<UserFollow> userFollowList = userFollowService.findFollows(userId, pageable);
            if (CollectionUtil.isEmpty(userFollowList)) {
                return ResponseEntity.failure(Errors.USER_NOT_FOLLOW);
            }
            userInfoBeanList = new ArrayList<>();
            for (UserFollow userFollow : userFollowList) {
                UserInfo follow = userInfoService.findById(userFollow.getEndUserId());
                UserInfoBean userInfoBean = new UserInfoBean(follow);
                userInfoBeanList.add(userInfoBean);
            }
        } else {
            //显示谁关注了此用户
            List<UserFollow> userFollowList = userFollowService.findFollowing(userId, pageable);
            if (CollectionUtil.isEmpty(userFollowList)) {
                return ResponseEntity.failure(Errors.USER_NOT_FOLLOW);
            }
            userInfoBeanList = new ArrayList<>();
            for (UserFollow userFollow : userFollowList) {
                UserInfo following = userInfoService.findById(userFollow.getEndUserId());
                UserInfoBean userInfoBean = new UserInfoBean(following);
                userInfoBeanList.add(userInfoBean);
            }
        }
        return ResponseEntity.success().set(BBSConsts.DATA, userInfoBeanList);
    }
}
