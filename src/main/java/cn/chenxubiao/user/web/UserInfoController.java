package cn.chenxubiao.user.web;

import cn.chenxubiao.account.domain.Account;
import cn.chenxubiao.account.service.AccountService;
import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.project.domain.ProjectLike;
import cn.chenxubiao.project.service.ProjectInfoService;
import cn.chenxubiao.project.service.ProjectLikeService;
import cn.chenxubiao.project.service.ProjectViewService;
import cn.chenxubiao.user.bean.UserBean;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.service.UserFollowService;
import cn.chenxubiao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenxb on 17-5-14.
 */
@RestController
public class UserInfoController extends CommonController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserFollowService userFollowService;
    @Autowired
    private ProjectLikeService projectLikeService;
    @Autowired
    private ProjectViewService projectViewService;
    @Autowired
    private ProjectInfoService projectInfoService;

    @RequestMapping(value = "/user/info/data", method = RequestMethod.GET)
    public ResponseEntity getUserInfo(HttpServletRequest request) {
        UserSession userSession = super.getUserSession(request);
        int userId = userSession.getUserId();
        int follows = userFollowService.countFollows(userId);
        int following = userFollowService.countFollowing(userId);
        int projectNum = projectInfoService.countProjectNum(userId);
        int otherViews = projectViewService.countUserPicViewNum(userId);
        int viewOthers = projectViewService.countByViewer(userId);
        int likes = projectLikeService.countByPicOwner(userId);
        Account account = accountService.findByUserId(userId);
        int money = account.getTotalMoney();

        UserBean userBean = new UserBean();
        userBean.setUserId(userId);
        userBean.setAvatarId(userSession.getAvatarId());
        userBean.setUserName(userSession.getUserName());
        userBean.setFollowing(following);
        userBean.setPicNum(projectNum);
        userBean.setFollows(follows);
        userBean.setOtherViews(otherViews);
        userBean.setViewOthers(viewOthers);
        userBean.setLikeNum(likes);
        userBean.setMoney(money);

        return ResponseEntity.success().set(BBSConsts.DATA, userBean);
    }
}
