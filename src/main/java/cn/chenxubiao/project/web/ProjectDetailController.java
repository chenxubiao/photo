package cn.chenxubiao.project.web;

import cn.chenxubiao.common.bean.CommonBean;
import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.picture.domain.PictureExif;
import cn.chenxubiao.picture.service.PictureExifService;
import cn.chenxubiao.project.bean.ProjectDetailBean;
import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.project.domain.ProjectTag;
import cn.chenxubiao.project.domain.ProjectView;
import cn.chenxubiao.project.service.ProjectInfoService;
import cn.chenxubiao.project.service.ProjectLikeService;
import cn.chenxubiao.project.service.ProjectTagService;
import cn.chenxubiao.project.service.ProjectViewService;
import cn.chenxubiao.tag.domain.TagCategory;
import cn.chenxubiao.tag.domain.TagInfo;
import cn.chenxubiao.tag.service.TagCategoryService;
import cn.chenxubiao.tag.service.TagInfoService;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.service.UserFollowService;
import cn.chenxubiao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenxb on 17-5-8.
 */
@RestController
public class ProjectDetailController extends CommonController {

    @Autowired
    private ProjectInfoService projectInfoService;
    @Autowired
    private PictureExifService pictureExifService;
    @Autowired
    private TagCategoryService tagCategoryService;
    @Autowired
    private TagInfoService tagInfoService;
    @Autowired
    private ProjectTagService projectTagService;
    @Autowired
    private ProjectViewService projectViewService;
    @Autowired
    private UserFollowService userFollowService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ProjectLikeService projectLikeService;

    @RequestMapping(value = "/project/detail/data", method = RequestMethod.GET)
    public ResponseEntity getProjectDetail(int picId, HttpServletRequest request) {

        if (picId <= 0) {
            return ResponseEntity.failure(Errors.PARAMETER_ILLEGAL);
        }

        ProjectInfo projectInfo = projectInfoService.findByPicId(picId);
        if (projectInfo == null) {
            return ResponseEntity.failure(Errors.PICTURE_NOT_FOUND);
        }

        ProjectDetailBean projectDetailBean = new ProjectDetailBean();
        //用户看了谁的照片记录
        UserSession userSession = super.getUserSession(request);

        int viewNum = projectViewService.countByProjectId(projectInfo.getId());
        projectDetailBean.setViewNum(viewNum);

        if (userSession.getUserId() != projectInfo.getUserId()) {
            ProjectView projectView = new ProjectView(projectInfo, userSession.getUserId());
            projectView.setCreateTime(new Date());
            projectView.setModifyTime(projectView.getCreateTime());
            projectViewService.save(projectView);

            projectDetailBean.setIsSelf(BBSConsts.UserSelf.NOT_SELF);
            boolean isFollow = userFollowService.isFollowed(projectInfo.getUserId(), userSession.getUserId());
            if (isFollow) {
                projectDetailBean.setIsFollow(BBSConsts.UserFollow.FOLLOW);
            } else {
                projectDetailBean.setIsFollow(BBSConsts.UserFollow.NOT_FOLLOW);
            }

            UserInfo userInfo = userInfoService.findById(projectInfo.getUserId());
            projectDetailBean.setUserName(userInfo.getUserName());
            projectDetailBean.setAvatarId(userInfo.getAvatarId());
        } else {
            projectDetailBean.setUserName(userSession.getUserName());
            projectDetailBean.setIsSelf(BBSConsts.UserSelf.SELF);
            projectDetailBean.setIsFollow(BBSConsts.UserFollow.NOT_FOLLOW);
            projectDetailBean.setAvatarId(userSession.getAvatarId());
        }

        if (userSession.getUserId() == projectInfo.getUserId()) {
            projectDetailBean.setAuth(1);
            projectDetailBean.setMoney(0);
        }else {
            projectDetailBean.setAuth(projectInfo.getAuth());
            projectDetailBean.setMoney(projectInfo.getMoney());
        }
        projectDetailBean.setProjectId(projectInfo.getId());
        projectDetailBean.setDescription(projectInfo.getDescription());
        projectDetailBean.setUserId(projectInfo.getUserId());
        projectDetailBean.setPicId(picId);
        int liked = projectLikeService.isLiked(userSession.getUserId(), projectInfo.getId());
        int likeNum = projectLikeService.countProjectLikeNum(projectInfo.getId());
        projectDetailBean.setLiked(liked);
        projectDetailBean.setLikeNum(likeNum);
        PictureExif pictureExif = pictureExifService.findByPicId(picId);
        projectDetailBean.setExif(pictureExif);
        ProjectInfo former = projectInfoService.findFormerProject(projectInfo);
        if (former == null) {
            projectDetailBean.setStartPicId(0);
        } else {
            projectDetailBean.setStartPicId(former.getPicId());
        }
        ProjectInfo latter = projectInfoService.findLatterProject(projectInfo);
        if (latter == null) {
            projectDetailBean.setEndPicId(0);
        } else {
            projectDetailBean.setEndPicId(latter.getPicId());
        }
        CommonBean category = null;
        if (projectInfo.getCategoryId() > 0) {
            TagCategory tagCategory = tagCategoryService.findById(projectInfo.getCategoryId());
            if (tagCategory != null) {
                category = new CommonBean(tagCategory.getId(), tagCategory.getName());
            }
        }
        if (category == null) {
            category = new CommonBean(0, "未知分类");
        }
        projectDetailBean.setCategory(category);
        List<ProjectTag> projectTagList = projectTagService.findByProjectId(projectInfo.getId());
        if (CollectionUtil.isNotEmpty(projectTagList)) {
            List<Integer> tagIds = new ArrayList<>();
            for (ProjectTag projectTag : projectTagList) {
                tagIds.add(projectTag.getTagId());
            }
            List<TagInfo> tagInfoList = tagInfoService.findInIds(tagIds);
            if (CollectionUtil.isNotEmpty(tagInfoList)) {
                List<CommonBean> tag = new ArrayList<>();
                for (TagInfo tagInfo : tagInfoList) {
                    CommonBean tagCommonBean = new CommonBean(tagInfo.getId(), tagInfo.getName());
                    tag.add(tagCommonBean);
                }
                projectDetailBean.setTag(tag);
            }
        }

        return ResponseEntity.success().set(BBSConsts.DATA, projectDetailBean);
    }

}
