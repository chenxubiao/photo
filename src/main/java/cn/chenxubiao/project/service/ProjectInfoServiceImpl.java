package cn.chenxubiao.project.service;

import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.picture.bean.PicInfoBean;
import cn.chenxubiao.picture.domain.PictureExif;
import cn.chenxubiao.picture.service.PictureExifService;
import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.project.repository.ProjectInfoRepository;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public void save(ProjectInfo projectInfo) {
        if (projectInfo == null) {
            return;
        }
        ProjectInfo projectInfoDB = findByPicId(projectInfo.getPicId());
        if (projectInfoDB != null) {
            return;
        }
        projectInfoRepository.save(projectInfo);
    }
}
