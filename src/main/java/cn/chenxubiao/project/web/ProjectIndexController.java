package cn.chenxubiao.project.web;

import cn.chenxubiao.common.bean.Pagination;
import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.picture.domain.PictureExif;
import cn.chenxubiao.picture.service.PictureExifService;
import cn.chenxubiao.project.bean.PicInfo;
import cn.chenxubiao.project.bean.ProjectBean;
import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.project.service.ProjectInfoService;
import cn.chenxubiao.project.service.ProjectLikeService;
import cn.chenxubiao.user.domain.UserFollow;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.service.UserFollowService;
import cn.chenxubiao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    private UserInfoService userInfoService;
    @Autowired
    private ProjectLikeService projectLikeService;
    @Autowired
    private PictureExifService pictureExifService;


    @RequestMapping(value = "/project/following/list", method = RequestMethod.GET)
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
        List<PicInfo> picInfoList = getPicInfoList(projectInfoPage.getContent(), userId);
        Pagination pagination = new Pagination(projectInfoPage.getTotalPages()
                , (new Long(projectInfoPage.getTotalElements())).intValue(), pageable.getPageNumber(), pageable.getPageSize());

        return ResponseEntity.success().set(BBSConsts.DATA, picInfoList).set(BBSConsts.PAGINATION, pagination);
    }

    @RequestMapping(value = "/project/latest/list", method = RequestMethod.GET)
    public ResponseEntity findProjectList(HttpServletRequest request, Pageable pageable) {
        UserSession userSession = super.getUserSession(request);
        int userId = userSession.getUserId();
        Page<ProjectInfo> projectInfoPage = projectInfoService.findByPage(pageable);

        if (CollectionUtil.isEmpty(projectInfoPage.getContent())) {
            return ResponseEntity.failure(Errors.FOLLOWING_NOT_UPLOAD_PROJECT);
        }
        List<PicInfo> picInfoList = getPicInfoList(projectInfoPage.getContent(), userId);
        Pagination pagination = new Pagination(projectInfoPage.getTotalPages()
                , new Long(projectInfoPage.getTotalElements()).intValue(), pageable.getPageNumber(), pageable.getPageSize());

        return ResponseEntity.success().set(BBSConsts.DATA, picInfoList).set(BBSConsts.PAGINATION, pagination);
    }

    public List<PicInfo> getPicInfoList(List<ProjectInfo> projectInfoList, int userId) {

        if (CollectionUtil.isEmpty(projectInfoList)) {
            return null;
        }
        List<PicInfo> picInfoList = new ArrayList<>();
        for (ProjectInfo projectInfo : projectInfoList) {
            PictureExif pictureExif = pictureExifService.findByPicId(projectInfo.getPicId());
            if (pictureExif == null) {
                continue;
            }
            UserInfo userInfo = userInfoService.findById(projectInfo.getUserId());
            PicInfo picInfo = new PicInfo(userInfo, projectInfo, pictureExif);
            picInfo.setIsFollow(BBSConsts.UserFollow.FOLLOW);
            picInfo.setIsSelf(0);
            int follows = userFollowService.countFollows(userInfo.getId());
            int followings = userFollowService.countFollowing(userInfo.getId());
            int isLiked = projectLikeService.isLiked(userId, projectInfo.getId());
            int likeNum = projectLikeService.countProjectLikeNum(projectInfo.getId());
            picInfo.setFollowing(followings);
            picInfo.setFollows(follows);
            picInfo.setIsLiked(isLiked);
            picInfo.setLikeNum(likeNum);

            picInfoList.add(picInfo);
        }
        return picInfoList;
    }
}
