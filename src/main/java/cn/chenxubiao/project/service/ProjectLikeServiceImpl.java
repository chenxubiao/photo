package cn.chenxubiao.project.service;

import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.message.domain.Message;
import cn.chenxubiao.message.service.MessageService;
import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.project.domain.ProjectLike;
import cn.chenxubiao.project.repository.ProjectLikeRepository;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenxb on 17-4-1.
 */
@Service
public class ProjectLikeServiceImpl implements ProjectLikeService {
    @Autowired
    private ProjectLikeRepository projectLikeRepository;
    @Autowired
    private ProjectInfoService projectInfoService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MessageService messageService;

    @Override
    public void disposeLike(int userId, int projectId) {
        if (userId <= 0 || projectId <= 0) {
            return;
        }

        ProjectInfo projectInfo = projectInfoService.findById(projectId);
        if (projectInfo == null) {
            return;
        }
        UserInfo userInfo = userInfoService.findById(userId);
        if (userInfo == null) {
            return;
        }

        ProjectLike projectLike = projectLikeRepository.findByUserIdAndProjectId(userId, projectId);
        if (projectLike == null) {

            if (userInfo.getId() != projectInfo.getUserId()) {
                Message message = new Message();
                message.setType(BBSConsts.MessageType.PROJECT_LIKE);
                message.setSender(userInfo.getId());
                message.setProjectId(projectId);
                message.setReceiver(projectInfo.getUserId());
                message.setStatus(BBSConsts.MessageStatus.SEND);
                message.setMessage(BBSConsts.LIKED + projectInfo.getTitle());
                message.setCreateTime(new Date());
                message.setModifyTime(message.getCreateTime());
                messageService.save(message);
            }

            projectLike = new ProjectLike(userId, projectId);
            projectLike.setCreateTime(new Date());
            projectLike.setModifyTime(projectLike.getCreateTime());
            projectLikeRepository.save(projectLike);
        } else {

            if (userInfo.getId() != projectInfo.getUserId()) {
                Message message = new Message();
                message.setSender(userInfo.getId());
                message.setType(BBSConsts.MessageType.PROJECT_LIKE);
                message.setProjectId(projectId);
                message.setReceiver(projectInfo.getUserId());
                message.setStatus(BBSConsts.MessageStatus.SEND);
                message.setMessage(BBSConsts.UNLIKE + projectInfo.getTitle());
                message.setCreateTime(new Date());
                message.setModifyTime(message.getCreateTime());
                messageService.save(message);
            }

            projectLikeRepository.delete(projectLike);
        }
    }

    @Override
    public int isLiked(int userId, int projectId) {
        if (userId <= 0 || projectId <= 0) {
            return 0;
        }
        return projectLikeRepository.countByUserIdAndProjectId(userId, projectId);
    }

    @Override
    public int countProjectLikeNum(int projectId) {
        if (projectId <= 0) {
            return 0;
        }
        return projectLikeRepository.countByProjectId(projectId);
    }

    @Override
    public int countByPicOwner(int userId) {
        if (userId <= 0) {
            return 0;
        }
        List<ProjectInfo> projectInfoList = projectInfoService.findByUserId(userId);
        if (CollectionUtil.isEmpty(projectInfoList)) {
            return 0;
        }
        List<Integer> projectIds = new ArrayList<>();
        for (ProjectInfo projectInfo : projectInfoList) {
            projectIds.add(projectInfo.getId());
        }
        return projectLikeRepository.countByProjectIdIn(projectIds);
    }

    @Override
    public List<ProjectLike> findPopular(Pageable pageable) {
//        return projectLikeRepository.find
        return null;
    }
}
