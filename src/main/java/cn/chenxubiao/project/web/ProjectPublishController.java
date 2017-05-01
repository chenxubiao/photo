package cn.chenxubiao.project.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.NumberUtil;
import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.picture.domain.Attachment;
import cn.chenxubiao.picture.service.AttachmentService;
import cn.chenxubiao.project.bean.ProjectInfoBean;
import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.project.domain.ProjectTag;
import cn.chenxubiao.project.service.ProjectInfoService;
import cn.chenxubiao.project.service.ProjectTagService;
import cn.chenxubiao.tag.domain.TagCategory;
import cn.chenxubiao.tag.service.TagCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by chenxb on 17-4-29.
 */
@RestController
public class ProjectPublishController extends CommonController {
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private TagCategoryService tagCategoryService;
    @Autowired
    private ProjectInfoService projectInfoService;
    @Autowired
    private ProjectTagService projectTagService;

    @RequestMapping(value = "/project/publish/data", method = RequestMethod.POST)
    public ResponseEntity publishProject(ProjectInfoBean projectInfoBean, HttpSession session, HttpServletRequest request) {

        UserSession userSession = getUserSession(request);
        int userId = userSession.getUserId();
        if (projectInfoBean == null
                || projectInfoBean.getPicId() <= 0) {
            return ResponseEntity.failure(Errors.PARAMETER_ILLEGAL);
        }
        projectInfoBean.setUserId(userId);
        projectInfoBean.setDescription(StringUtil.isBlank(projectInfoBean.getDescription()) == true ? ""
                : projectInfoBean.getDescription().trim());

        Attachment attachment = attachmentService.findById(projectInfoBean.getPicId());
        if (attachment == null) {
            return ResponseEntity.failure(Errors.PICTURE_NOT_FOUND);
        }
        if (projectInfoBean.getCategoryId() > 0) {
            TagCategory tagCategory = tagCategoryService.findById(projectInfoBean.getCategoryId());
            if (tagCategory == null) {
                return ResponseEntity.failure(Errors.TAG_CATEGORY_NOT_FOUND);
            }
        } else {
            projectInfoBean.setCategoryId(0);
        }
        ProjectInfo projectInfo = new ProjectInfo(projectInfoBean);
        projectInfo.setCreateTime(new Date());
        projectInfo.setModifyTime(projectInfo.getCreateTime());
        projectInfoService.save(projectInfo);
        List<ProjectTag> projectTagList = new ArrayList<>();
        if (StringUtil.isNotBlank(projectInfoBean.getTagIds())) {
            Set<Integer> tagIdSet = NumberUtil.parseToIntSet(projectInfoBean.getTagIds());
            if (tagIdSet != null) {
                for (int id : tagIdSet) {
                    ProjectTag projectTag = new ProjectTag(userId, projectInfo.getId(), id);
                    projectTag.setCreateTime(new Date());
                    projectTag.setModifyTime(projectTag.getCreateTime());
                    projectTagList.add(projectTag);
                }
                projectTagService.saveAll(projectTagList);
            }
        }
        return ResponseEntity.success().set(BBSConsts.DATA, projectInfo).set("tag", projectTagList);
    }
}
