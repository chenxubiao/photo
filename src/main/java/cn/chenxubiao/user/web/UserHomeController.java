package cn.chenxubiao.user.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.message.bean.MessageBean;
import cn.chenxubiao.message.domain.Message;
import cn.chenxubiao.message.service.MessageService;
import cn.chenxubiao.picture.domain.Attachment;
import cn.chenxubiao.picture.domain.PictureExif;
import cn.chenxubiao.picture.service.AttachmentService;
import cn.chenxubiao.picture.service.PictureExifService;
import cn.chenxubiao.project.bean.ProjectBean;
import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.project.service.ProjectInfoService;
import cn.chenxubiao.project.service.ProjectLikeService;
import cn.chenxubiao.project.service.ProjectViewService;
import cn.chenxubiao.user.bean.UserHomeBean;
import cn.chenxubiao.user.bean.UserProfileBean;
import cn.chenxubiao.user.domain.UserHobby;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.domain.UserTool;
import cn.chenxubiao.user.service.UserFollowService;
import cn.chenxubiao.user.service.UserHobbyService;
import cn.chenxubiao.user.service.UserInfoService;
import cn.chenxubiao.user.service.UserToolService;
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
    private ProjectLikeService projectLikeService;
    @Autowired
    private ProjectViewService projectViewService;
    @Autowired
    private UserFollowService userFollowService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private PictureExifService pictureExifService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private UserHobbyService userHobbyService;
    @Autowired
    private UserToolService userToolService;
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/user/home/data", method = RequestMethod.GET)
    public ResponseEntity getUserHomeData(@RequestParam(value = "userId", defaultValue = "0") int userId,
                                          HttpServletRequest request, Pageable pageable) {

        UserInfo userInfo;
        UserSession userSession = getUserSession(request);
        if (userId > 0) {
            userInfo = userInfoService.findById(userId);
        } else {
            userInfo = userInfoService.findById(userSession.getUserId());
        }
        if (userInfo == null) {
            return ResponseEntity.failure(Errors.USER_INFO_NOT_FOUND);
        }
        userId = userInfo.getId();
        int followers = userFollowService.countFollows(userId);
        int following = userFollowService.countFollowing(userId);
        int views = projectViewService.countUserPicViewNum(userId);
        int likes = projectLikeService.countByPicOwner(userId);

        UserHomeBean userHomeBean = new UserHomeBean(likes, views, following, followers, userInfo, null);
        if (userId == userSession.getUserId()) {
            userHomeBean.setIsSelf(BBSConsts.UserSelf.SELF);
            userHomeBean.setIsFollow(BBSConsts.UserFollow.NOT_FOLLOW);
        } else {
            userHomeBean.setIsSelf(BBSConsts.UserSelf.NOT_SELF);
            boolean isFollow = userFollowService.isFollowed(userId, userSession.getUserId());
            if (isFollow) {
                userHomeBean.setIsFollow(BBSConsts.UserFollow.FOLLOW);
            }else {
                userHomeBean.setIsFollow(BBSConsts.UserFollow.NOT_FOLLOW);
            }
        }

        List<UserHobby> userHobbyList = userHobbyService.findByUserId(userId);
        List<Integer> categoryIds = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userHobbyList)) {
            for (UserHobby userHobby : userHobbyList) {
                categoryIds.add(userHobby.getCategoryId());
            }
        }
        List<UserTool> userToolList = userToolService.findByUserId(userId);
        StringBuilder camera = new StringBuilder();
        StringBuilder len = new StringBuilder();
        StringBuilder tool = new StringBuilder();
        boolean isCameraFirst = true;
        boolean isLenFirst = true;
        boolean isToolFirst = true;
        if (CollectionUtil.isNotEmpty(userToolList)) {
            for (UserTool userTool : userToolList) {
                if (userTool.getType() == BBSConsts.UserToolType.CAMERA) {
                    if (isCameraFirst) {
                        camera.append(userTool.getName());
                        isCameraFirst = false;
                    } else {
                        camera.append("," + userTool.getName());
                    }
                } else if (userTool.getType() == BBSConsts.UserToolType.LEN) {
                    if (isLenFirst) {
                        len.append(userTool.getName());
                        isLenFirst = false;
                    } else {
                        len.append("," + userTool.getName());
                    }
                } else if (userTool.getType() == BBSConsts.UserToolType.TOOL) {
                    if (isToolFirst) {
                        tool.append(userTool.getName());
                        isToolFirst = false;
                    } else {
                        tool.append("," + userTool.getName());
                    }
                }
            }
        }

        UserProfileBean userProfileBean = new UserProfileBean(userInfo);
        userProfileBean.setHobby(categoryIds);
        userProfileBean.setCameraNames(camera.toString());
        userProfileBean.setLensNames(len.toString());
        userProfileBean.setToolNames(tool.toString());
        userHomeBean.setUserProfile(userProfileBean);

        List<Message> messageList = messageService.findUnLookMsg(userSession.getUserId());
        MessageBean messageBean = new MessageBean();
        if (CollectionUtil.isNotEmpty(messageList)) {
            messageBean.setMsgCount(messageList.size());
            messageBean.setMessages(messageList);
        }

        List<ProjectInfo> projectInfoList = projectInfoService.findByUserId(userId);
        List<ProjectBean> projectBeanList = null;
        if (CollectionUtil.isNotEmpty(projectInfoList)) {
            projectBeanList = new ArrayList<>();
            for (ProjectInfo projectInfo : projectInfoList) {
                if (projectInfo == null) {
                    continue;
                }
                int likeNum = projectLikeService.countProjectLikeNum(projectInfo.getId());
                int viewNum = projectViewService.countByProjectId(projectInfo.getId());
                PictureExif pictureExif = pictureExifService.findByPicId(projectInfo.getPicId());
                ProjectBean projectBean = new ProjectBean(projectInfo, likeNum, viewNum, pictureExif);
                if (StringUtil.isBlank(projectBean.getTitle())) {
                    Attachment attachment = attachmentService.findById(projectInfo.getId());
                    projectBean.setTitle(attachment.getFileName());
                }
                int liked = projectLikeService.isLiked(userSession.getUserId(), projectInfo.getId());
                projectBean.setLiked(liked);
                projectBean.setIsFollow(userHomeBean.getIsFollow());
                projectBean.setIsSelf(userHomeBean.getIsSelf());
                projectBeanList.add(projectBean);
            }

        }
        userHomeBean.setProject(projectBeanList);

        return ResponseEntity.success().set(BBSConsts.DATA, userHomeBean).set("msg", messageBean);
    }

    @RequestMapping(value = "/user/project/data", method = RequestMethod.GET)
    public ResponseEntity getUserInfo(@RequestParam(value = "userId", defaultValue = "0") int userId,
                                      HttpServletRequest request, Pageable pageable) {

        return getUserHomeData(userId, request, pageable);
    }

}
