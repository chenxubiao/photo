package cn.chenxubiao.project.service;

import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.picture.bean.PicInfoBean;
import cn.chenxubiao.picture.domain.PictureExif;
import cn.chenxubiao.picture.service.PictureExifService;
import cn.chenxubiao.project.bean.ProjectBean;
import cn.chenxubiao.project.bean.ProjectInfoBean;
import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.project.repository.ProjectInfoRepository;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by chenxb on 17-4-1.
 */
@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {
    @Autowired
    private ProjectInfoRepository projectInfoRepository;
    @Autowired
    private PictureExifService pictureExifService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ProjectLikeService projectLikeService;

    @Override
    public ProjectInfo findByPicId(int picId) {
        if (picId <= 0) {
            return null;
        }
        return projectInfoRepository.findByPicId(picId);
    }

    @Override
    public List<PicInfoBean> findPicInfoByPage(Pageable pageable) {
        return findPicInfoByPage(pageable, 0);
    }

    @Override
    public List<PicInfoBean> findPicInfoByPage(Pageable pageable, int userId) {

        List<PicInfoBean> picInfoBeanList = new ArrayList<>();

        Page<ProjectInfo> projectInfoPage = findByPage(pageable);
        List<ProjectInfo> projectInfoList = projectInfoPage.getContent();
        if (CollectionUtil.isEmpty(projectInfoList)) {
            return picInfoBeanList;
        }
        Set<Integer> picIds = new HashSet<>();
        Map<Integer, ProjectInfo> projectInfoMap = new HashMap<>();
        for (ProjectInfo projectInfo : projectInfoList) {
            picIds.add(projectInfo.getPicId());
            projectInfoMap.put(projectInfo.getPicId(), projectInfo);
        }
        List<PictureExif> pictureExifList = pictureExifService.findInPicIds(picIds);
        if (CollectionUtil.isEmpty(pictureExifList)) {
            return picInfoBeanList;
        }
        for (PictureExif pictureExif : pictureExifList) {
            int picId = pictureExif.getPicId();
            ProjectInfo projectInfo = projectInfoMap.get(picId);
            UserInfo user = userInfoService.findById(projectInfo.getUserId());
            if (user == null) {
                continue;
            }
            int isLiked = 0;
            if (userId > 0) {
                isLiked = projectLikeService.countByUserIdAndProjectId(userId, projectInfo.getId());
            }
            PicInfoBean picInfoBean = new PicInfoBean(pictureExif, isLiked, user);
            picInfoBeanList.add(picInfoBean);
        }
        return picInfoBeanList;
    }

    @Override
    public Page<ProjectInfo> findByPage(Pageable pageable) {
        return projectInfoRepository.findByPage(pageable);
    }

    @Override
    public ProjectInfo save(ProjectInfo projectInfo) {
        if (projectInfo == null) {
            return projectInfo;
        }
        ProjectInfo projectInfoDB = findByPicId(projectInfo.getPicId());
        if (projectInfoDB != null) {
            return projectInfoDB;
        }
        return projectInfoRepository.save(projectInfo);
    }

    @Override
    public ProjectInfo findProjectInDB(ProjectInfoBean projectInfoBean) {
        if (projectInfoBean == null) {
            return null;
        }
        ProjectInfo projectInfo;
        if (projectInfoBean.getId() > 0) {
            projectInfo = projectInfoRepository.findById(projectInfoBean.getId());
            if (projectInfo != null) {
                return projectInfo;
            }
        }
        if (projectInfoBean.getPicId() > 0) {
            projectInfo = projectInfoRepository.findByPicId(projectInfoBean.getPicId());
            if (projectInfo != null) {
                return projectInfo;
            }
        }
        return null;
    }

    @Override
    public List<ProjectInfo> findByUserAndPage(int userId, Pageable pageable) {

        if (userId <= 0) {
            return null;
        }

        Page<ProjectInfo> projectInfoPage = projectInfoRepository.findByUserIdAndPage(userId, pageable);
        return projectInfoPage.getContent();
    }

    @Override
    public ProjectInfo findFormerProject(ProjectInfo projectInfo) {
        if (projectInfo == null) {
            return null;
        }
        Pageable pageable = new PageRequest(1, 1, Sort.Direction.DESC, "id");
        Page<ProjectInfo> projectInfoPage = projectInfoRepository.findFormerProjectInfo
                (projectInfo.getId(), projectInfo.getUserId(), pageable);
        List<ProjectInfo> projectInfoList = projectInfoPage.getContent();
        if (CollectionUtil.isEmpty(projectInfoList)) {
            return null;
        }
        return projectInfoList.get(0);
    }

    @Override
    public ProjectInfo findLatterProject(ProjectInfo projectInfo) {
        if (projectInfo == null) {
            return null;
        }
        Pageable pageable = new PageRequest(1, 1, Sort.Direction.ASC, "id");
        Page<ProjectInfo> projectInfoPage = projectInfoRepository.findLatterProjectInfo
                (projectInfo.getId(), projectInfo.getUserId(), pageable);
        List<ProjectInfo> projectInfoList = projectInfoPage.getContent();
        if (CollectionUtil.isEmpty(projectInfoList)) {
            return null;
        }
        return projectInfoList.get(0);
    }

    @Override
    public List<ProjectInfo> findByUserId(int userId) {
        if (userId <= 0) {
            return null;
        }
        return projectInfoRepository.findByUserId(userId);
    }

    @Override
    public ProjectInfo findById(int id) {
        if (id <= 0) {
            return null;
        }
        return projectInfoRepository.findById(id);
    }

    @Override
    public Page<ProjectInfo> findInUserIdAndPage(List<Integer> userIds, Pageable pageable) {
        if (CollectionUtil.isEmpty(userIds)) {
            return null;
        }
        return projectInfoRepository.findAllByUserIdIn(userIds, pageable);
    }

    @Override
    public List<ProjectBean> search(String name, int sessonUserId) {
        if (StringUtil.isBlank(name)) {
            return null;
        }
        List<ProjectInfo> projectInfoList = projectInfoRepository
                .findDistinctByTitleLikeOrDescriptionLike(name, name);

        return null;
    }

}
