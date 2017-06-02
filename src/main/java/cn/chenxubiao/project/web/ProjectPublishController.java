package cn.chenxubiao.project.web;

import cn.chenxubiao.account.domain.Account;
import cn.chenxubiao.account.domain.AccountLog;
import cn.chenxubiao.account.enums.AccountLogTypeEnum;
import cn.chenxubiao.account.service.AccountLogService;
import cn.chenxubiao.account.service.AccountService;
import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.NumberUtil;
import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.message.domain.Message;
import cn.chenxubiao.message.enums.MessageTypeEnum;
import cn.chenxubiao.message.service.MessageService;
import cn.chenxubiao.picture.domain.Attachment;
import cn.chenxubiao.picture.service.AttachmentService;
import cn.chenxubiao.picture.service.PictureExifService;
import cn.chenxubiao.project.bean.ProjectInfoBean;
import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.project.domain.ProjectTag;
import cn.chenxubiao.project.service.ProjectInfoService;
import cn.chenxubiao.project.service.ProjectTagService;
import cn.chenxubiao.tag.domain.TagCategory;
import cn.chenxubiao.tag.domain.TagInfo;
import cn.chenxubiao.tag.service.TagCategoryService;
import cn.chenxubiao.tag.service.TagInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by chenxb on 17-4-29.
 */
@RestController
public class ProjectPublishController extends CommonController {
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private PictureExifService pictureExifService;
    @Autowired
    private TagCategoryService tagCategoryService;
    @Autowired
    private ProjectInfoService projectInfoService;
    @Autowired
    private ProjectTagService projectTagService;
    @Autowired
    private TagInfoService tagInfoService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountLogService accountLogService;
    @Autowired
    private MessageService messageService;


    @RequestMapping(value = "/project/publish/data", method = RequestMethod.POST)
    public ResponseEntity publishProject(ProjectInfoBean projectInfoBean, HttpServletRequest request) {

        UserSession userSession = getUserSession(request);
        int userId = userSession.getUserId();
        if (projectInfoBean == null
                || projectInfoBean.getPicId() <= 0) {
            return ResponseEntity.failure(Errors.PARAMETER_ILLEGAL);
        }
        projectInfoBean.setUserId(userId);
        projectInfoBean.setDescription(projectInfoBean.getDescription() == null ? ""
                : projectInfoBean.getDescription().trim());

        Attachment attachment = attachmentService.findById(projectInfoBean.getPicId());
        if (attachment == null) {
            return ResponseEntity.failure(Errors.PICTURE_NOT_FOUND);
        }
        int picExif = pictureExifService.countByAttachmentId(attachment.getId());
        if (picExif <= 0) {
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
        if (StringUtil.isBlank(projectInfoBean.getTitle())) {
            projectInfoBean.setTitle(attachment.getFileName());
        } else {
            projectInfoBean.setTitle(projectInfoBean.getTitle().trim());
        }
        ProjectInfo projectInfo;
        boolean isModify = false;
        ProjectInfo projectInfoDB = projectInfoService.findProjectInDB(projectInfoBean);
        if (projectInfoDB != null) {
            isModify = true;
            projectInfo = projectInfoDB;
            boolean isProjectInfoDBModify = false;
            if (!projectInfo.getTitle().equals(projectInfoBean.getTitle())) {
                isProjectInfoDBModify = true;
                projectInfo.setTitle(projectInfo.getTitle());
            }
            if (!projectInfo.getDescription().equals(projectInfoBean.getDescription())
                    && StringUtil.isNotBlank(projectInfoBean.getDescription())) {

                isProjectInfoDBModify = true;
                String description = projectInfoBean.getDescription().trim();
                projectInfo.setDescription(description);
            }
            if (isProjectInfoDBModify) {
                projectInfo.setModifyTime(new Date());
                projectInfoService.save(projectInfo);
            }
        }else {
            projectInfo = new ProjectInfo(projectInfoBean);
            projectInfo.setCreateTime(new Date());
            projectInfo.setModifyTime(projectInfo.getCreateTime());

            int pay = 10;
            Account account = accountService.findByUserId(userId);
            int total = account.getTotalMoney();
            if (total < pay) {
                return ResponseEntity.failure(Errors.ACCOUNT_BALANCE);
            }
            projectInfo.setAuth(projectInfoBean.getAuth() == BBSConsts.ProjectAuth.AUTH ? 1 : 0);
            if (projectInfo.getAuth() == BBSConsts.ProjectAuth.AUTH) {
                if (projectInfoBean.getMoney() <= 0) {
                    return ResponseEntity.failure(Errors.PUBLISH_MONEY_ERROR);
                }
                projectInfo.setMoney(projectInfoBean.getMoney());
            }

            total = total - pay;
            account.setTotalMoney(total);
            account.setModifyTime(new Date());
            accountService.save(account);
            projectInfoService.save(projectInfo);

            AccountLog accountLog = new AccountLog
                    (userId, AccountLogTypeEnum.DEL_PIC_UPLOAD.getCode(), pay, projectInfo.getId(), projectInfo.getTitle(), account);
            accountLog.setBalance(total);
            accountLog.setCreateTime(new Date());
            accountLog.setModifyTime(accountLog.getCreateTime());
            accountLogService.save(accountLog);

            Message message = new Message
                    (MessageTypeEnum.ACCOUNT_CHANGE.getCode(), 1, userId, accountLog.getId(), accountLog.getMessage());
            message.setCreateTime(new Date());
            message.setModifyTime(message.getCreateTime());
            messageService.save(message);
        }
        List<ProjectTag> projectTagList = new ArrayList<>();
        String tagIds = projectInfoBean.getTagIds();
        if (StringUtil.isNotBlank(tagIds)) {
            String[] tagStrings = tagIds.split(",");
            Set<Integer> tagIdSet = new HashSet<>();
            for (String tagId : tagStrings) {
                if (StringUtil.isNotBlank(tagId)) {
                    if (NumberUtil.is(tagId)) {
                        int id = NumberUtil.parseIntQuietly(tagId.trim());
                        if (id > 0) {
                            TagInfo tagInfo = tagInfoService.findById(id);
                            if (tagInfo != null) {
                                tagIdSet.add(id);
                            }
                        }
                    } else {
                        String tagName = tagId.trim();
                        TagInfo tagInfo = new TagInfo();
                        tagInfo.setName(tagName);
                        tagInfo.setCreateTime(new Date());
                        tagInfo.setModifyTime(tagInfo.getCreateTime());
                        tagInfoService.save(tagInfo);
                        tagIdSet.add(tagInfo.getId());
                    }
                    if (isModify) {
                        List<ProjectTag> projectTagListDB = projectTagService.findByProjectId(projectInfo.getId());
                        if (CollectionUtil.isNotEmpty(projectTagListDB) && CollectionUtil.isNotEmpty(tagIdSet)) {
                            List<ProjectTag> projectTagsDB = new ArrayList<>();
                            for (ProjectTag projectTag : projectTagListDB) {
                                if (tagIdSet.contains(projectTag.getTagId())) {
                                    tagIdSet.remove(projectTag.getTagId());
                                }else {
                                    projectTagsDB.add(projectTag);
                                }
                            }
                            if (CollectionUtil.isNotEmpty(projectTagsDB)) {
                                projectTagService.deleteAll(projectTagsDB);
                            }
                        }
                    }
                }
            }
            if (tagIdSet.size() > 0) {

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
