package cn.chenxubiao.project.service;

import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.picture.bean.PicInfoBean;
import cn.chenxubiao.picture.domain.PictureExif;
import cn.chenxubiao.picture.service.PictureExifService;
import cn.chenxubiao.project.bean.ProjectBean;
import cn.chenxubiao.project.bean.ProjectInfoBean;
import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.project.domain.ProjectTag;
import cn.chenxubiao.project.repository.ProjectInfoRepository;
import cn.chenxubiao.tag.domain.TagCategory;
import cn.chenxubiao.tag.domain.TagInfo;
import cn.chenxubiao.tag.service.TagCategoryService;
import cn.chenxubiao.tag.service.TagInfoService;
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
    @Autowired
    private TagInfoService tagInfoService;
    @Autowired
    private TagCategoryService tagCategoryService;
    @Autowired
    private ProjectTagService projectTagService;

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
                isLiked = projectLikeService.isLiked(userId, projectInfo.getId());
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
        return projectInfoRepository.findFirstByUserIdAndIdBeforeOrderByIdDesc(projectInfo.getUserId(), projectInfo.getId());
//        Pageable pageable = new PageRequest(1, 1, Sort.Direction.DESC, "id");
//        Page<ProjectInfo> projectInfoPage = projectInfoRepository.findFormerProjectInfo
//                (projectInfo.getId(), projectInfo.getUserId(), pageable);
//        List<ProjectInfo> projectInfoList = projectInfoPage.getContent();
//        if (CollectionUtil.isEmpty(projectInfoList)) {
//            return null;
//        }
//        return projectInfoList.get(0);
    }

    @Override
    public ProjectInfo findLatterProject(ProjectInfo projectInfo) {
        if (projectInfo == null) {
            return null;
        }
        return projectInfoRepository.findFirstByUserIdAndIdAfter(projectInfo.getUserId(), projectInfo.getId());
//
//        Pageable pageable = new PageRequest(1, 1, Sort.Direction.ASC, "id");
//        Page<ProjectInfo> projectInfoPage = projectInfoRepository.findLatterProjectInfo
//                (projectInfo.getId(), projectInfo.getUserId(), pageable);
//        List<ProjectInfo> projectInfoList = projectInfoPage.getContent();
//        if (CollectionUtil.isEmpty(projectInfoList)) {
//            return null;
//        }
//        return projectInfoList.get(0);
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
        return projectInfoRepository.findAllByUserIdInOrderByIdDesc(userIds, pageable);
    }

    @Override
    public List<ProjectInfo> search(String name, int sessonUserId) {
        if (StringUtil.isBlank(name)) {
            return null;
        }
        return projectInfoRepository
                .findDistinctByTitleLikeOrDescriptionLikeOrderByIdDesc(name, name);
    }

    @Override
    public int countProjectNum(int userId) {
        if (userId <= 0) {
            return 0;
        }

        return projectInfoRepository.countByUserId(userId);
    }

    @Override
    public Page<ProjectInfo> findLatest(Pageable pageable) {
        return projectInfoRepository.findByPage(pageable);
    }

    @Override
    public List<ProjectInfo> searchByTag(String name) {
        List<TagCategory> tagCategoryList = tagCategoryService.search(name);
        Set<Integer> categoryIds = null;
        List<ProjectInfo> projectInfoList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(tagCategoryList)) {
            categoryIds = new HashSet<>();
            for (TagCategory tagCategory : tagCategoryList) {
                categoryIds.add(tagCategory.getId());
            }
        }
        if (CollectionUtil.isNotEmpty(categoryIds)) {
            projectInfoList = projectInfoRepository.findAllByCategoryIdInOrderByIdDesc(new ArrayList<>(categoryIds));
        }
        Set<Integer> tagIds = null;
        List<TagInfo> tagInfoList = tagInfoService.search(name);
        if (CollectionUtil.isNotEmpty(tagInfoList)) {
            tagIds = new HashSet<>();
            for (TagInfo tagInfo : tagInfoList) {
                tagIds.add(tagInfo.getId());
            }
        }
        if (CollectionUtil.isNotEmpty(tagIds)) {
            List<ProjectTag> projectTagList = projectTagService.findByTagIdIn(new ArrayList<>(tagIds));
            if (CollectionUtil.isNotEmpty(projectTagList)) {
                Set<Integer> projectIds = new HashSet<>();
                for (ProjectTag projectTag : projectTagList) {
                    projectIds.add(projectTag.getProjectId());
                }
                if (CollectionUtil.isNotEmpty(categoryIds)) {
                    List<ProjectInfo> projectTagUnCategoryList = projectInfoRepository
                            .findAllByCategoryIdNotInAndIdInOrderByIdDesc
                                    (new ArrayList<>(categoryIds), new ArrayList<>(projectIds));
                    projectInfoList.addAll(projectTagUnCategoryList);
                }else {
                    List<ProjectInfo> projectTagInList = projectInfoRepository
                            .findAllByIdInOrderByIdDesc(new ArrayList<>(projectIds));

                    projectInfoList.addAll(projectTagInList);
                }
            }
        }

        return projectInfoList;
    }

    private List<ProjectBean> getProjectBean(List<ProjectInfo> projectInfoList) {
        if (CollectionUtil.isEmpty(projectInfoList)) {
            return null;
        }
        List<ProjectBean> projectBeanList = new ArrayList<>();
        for (ProjectInfo projectInfo : projectInfoList) {
            PictureExif pictureExif = pictureExifService.findByPicId(projectInfo.getPicId());
            int likeNum = projectLikeService.countProjectLikeNum(projectInfo.getId());
            ProjectBean projectBean = new ProjectBean(projectInfo, likeNum, 0, pictureExif);
            projectBeanList.add(projectBean);
        }
        return projectBeanList;
    }

}
