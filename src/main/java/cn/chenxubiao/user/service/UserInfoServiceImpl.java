package cn.chenxubiao.user.service;

import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.project.domain.ProjectLike;
import cn.chenxubiao.project.domain.ProjectView;
import cn.chenxubiao.project.service.ProjectInfoService;
import cn.chenxubiao.project.service.ProjectLikeService;
import cn.chenxubiao.project.service.ProjectViewService;
import cn.chenxubiao.tag.domain.TagCategory;
import cn.chenxubiao.tag.service.TagCategoryService;
import cn.chenxubiao.user.bean.UserInfoBean;
import cn.chenxubiao.user.domain.UserHobby;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.domain.UserRole;
import cn.chenxubiao.user.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chenxb on 17-4-1.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserFollowService userFollowService;
    @Autowired
    private TagCategoryService tagCategoryService;
    @Autowired
    private UserHobbyService userHobbyService;
    @Autowired
    private ProjectLikeService projectLikeService;
    @Autowired
    private ProjectInfoService projectInfoService;
    @Autowired
    private ProjectViewService projectViewService;

    @Override
    public UserInfo findByEmail(String email) {
        if (StringUtil.isEmpty(email)) {
            return null;
        }
        UserInfo userInfo = userInfoRepository.findByEmail(email);
        return setUserRoleList(userInfo);
    }

    @Override
    public UserInfo findByCellphone(String cellphone) {
        if (StringUtil.isEmpty(cellphone)) {
            return null;
        }
        UserInfo userInfo = userInfoRepository.findByCellphone(cellphone);
        return setUserRoleList(userInfo);
    }

    @Override
    public UserInfo findByUserName(String userName) {
        if (StringUtil.isEmpty(userName)) {
            return null;
        }
        UserInfo userInfo = userInfoRepository.findByUserName(userName);
        return setUserRoleList(userInfo);
    }

    @Override
    public UserInfo loginByEmail(String email, String password) {
        if (StringUtil.isEmpty(email) || StringUtil.isEmpty(password)) {
            return null;
        }
        UserInfo userInfo = userInfoRepository.findByEmailAndPassword(email, password);
        return setUserRoleList(userInfo);
    }

    @Override
    public UserInfo loginByCellphone(String cellphone, String password) {
        if (StringUtil.isEmpty(cellphone) || StringUtil.isEmpty(password)) {
            return null;
        }
        UserInfo userInfo = userInfoRepository.findByCellphoneAndPassword(cellphone, password);
        return setUserRoleList(userInfo);
    }

    @Override
    public UserInfo loginByUserName(String userName, String password) {
        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)) {
            return null;
        }
        UserInfo userInfo = userInfoRepository.findByUserNameAndPassword(userName, password);
        return setUserRoleList(userInfo);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        }
        userInfo = userInfoRepository.save(userInfo);
        return setUserRoleList(userInfo);
    }

    @Override
    public boolean isEmailExist(String email) {
        if (StringUtil.isBlank(email)) {
            return false;
        }
        return userInfoRepository.countByEmail(email) > 0;
    }

    @Override
    public boolean isCellphoneExist(String cellphone) {
        if (StringUtil.isBlank(cellphone)) {
            return false;
        }
        return userInfoRepository.countByCellphone(cellphone) > 0;
    }

    @Override
    public boolean isUserNameExist(String userName) {
        if (StringUtil.isBlank(userName)) {
            return false;
        }
        return userInfoRepository.countByUserName(userName) > 0;
    }

    @Override
    public UserInfo findById(int id) {
        if (id <= 0) {
            return null;
        }
        UserInfo userInfo = userInfoRepository.findById(id);
        if (userInfo == null) {
            return null;
        }
        return setUserRoleList(userInfo);
    }

    @Override
    public UserInfo findByIdAndNormal(int id) {
        if (id <= 0) {
            return null;
        }
        UserInfo userInfo = userInfoRepository.findByIdAndStatus(id, BBSConsts.UserStatus.USER_IS_NORMAL);
        return setUserRoleList(userInfo);
    }

    @Override
    public List<UserInfoBean> search(String name) {
        return search(name, 0);
    }

    @Override
    public List<UserInfoBean> search(String name, int userId) {

        if (StringUtil.isBlank(name)) {
            return null;
        }
        List<UserInfo> userInfoList = null;
        List<TagCategory> tagCategoryList = tagCategoryService.search(name);
        Set<Integer> userIds = null;
        if (CollectionUtil.isNotEmpty(tagCategoryList)) {
            List<Integer> categoryIds = new ArrayList<>();
            for (TagCategory tagCategory : tagCategoryList) {
                categoryIds.add(tagCategory.getId());
            }
            List<UserHobby> userHobbyList = userHobbyService.findInCategoryId(categoryIds);
            if (CollectionUtil.isNotEmpty(userHobbyList)) {
                userIds = new HashSet<>();
                for (UserHobby userHobby : userHobbyList) {
                    userIds.add(userHobby.getUserId());
                }
            }
        }
        if (CollectionUtil.isNotEmpty(userIds)) {
            userInfoList = userInfoRepository.findAllByIdIn(new ArrayList<>(userIds));
        }
        if (CollectionUtil.isEmpty(userInfoList)) {
            userInfoList = userInfoRepository.findDistinctByUserNameLikeOrDescriptionLikeOrEmailLike(name, name, name);
        } else {
            List<UserInfo> userInfos = userInfoRepository.findDistinctByUserNameLikeOrDescriptionLikeOrEmailLikeAndIdNotIn
                    (name, name, name, new ArrayList<>(userIds));
            if (CollectionUtil.isNotEmpty(userInfos)) {
                userInfoList.addAll(userInfos);
            }
        }
        if (CollectionUtil.isEmpty(userInfoList)) {
            return null;
        }
        List<UserInfoBean> userInfoBeanList = new ArrayList<>();
        for (UserInfo userInfo : userInfoList) {
            if (userInfo.getId() == 1) {
                continue;
            }
            UserInfoBean userInfoBean = convertToUserInfoBean(userInfo, userId);
            userInfoBeanList.add(userInfoBean);
        }
        return userInfoBeanList;
    }

    @Override
    public UserInfoBean convertToUserInfoBean(UserInfo userInfo) {
        return convertToUserInfoBean(userInfo, 0);
    }

    @Override
    public UserInfoBean convertToUserInfoBean(UserInfo userInfo, int userId) {
        if (userInfo == null) {
            return null;
        }
        UserInfoBean userInfoBean = new UserInfoBean(userInfo);
        userInfoBean.setFollows(userFollowService.countFollows(userInfo.getId()));
        userInfoBean.setFollowing(userFollowService.countFollowing(userInfo.getId()));
        userInfoBean.setIsFollow(userFollowService.isFollow(userInfo.getId(), userId));
        return userInfoBean;
    }

    @Override
    public List<UserInfo> findPopular(int removeUserId) {
        Set<Integer> userIds = new HashSet<>();
        List<ProjectLike> projectLikeList = projectLikeService.findAll();
        if (CollectionUtil.isNotEmpty(projectLikeList)) {
            for (ProjectLike projectLike : projectLikeList) {
                ProjectInfo projectInfo = projectInfoService.findById(projectLike.getProjectId());
                if (removeUserId != projectInfo.getUserId()) {
                    userIds.add(projectInfo.getUserId());
                }
            }
        }
        List<ProjectView> projectViewList = projectViewService.findAllByUserIdIsNot(removeUserId);
        if (CollectionUtil.isNotEmpty(projectViewList)) {
            for (ProjectView projectView : projectViewList) {
                userIds.add(projectView.getUserId());
            }
        }
        if (CollectionUtil.isEmpty(userIds)) {
            return null;
        }
        return userInfoRepository.findAllByIdIn(new ArrayList<>(userIds));
    }

    @Override
    public List<UserInfo> findIdNotIn(List<Integer> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return null;
        }
        return userInfoRepository.findAllByIdNotIn(ids);
    }

    @Override
    public List<UserInfo> findByIdIn(List<Integer> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return null;
        }
        return userInfoRepository.findAllByIdInOrderByModifyTimeDesc(ids);
    }


    private UserInfo setUserRoleList(UserInfo userInfo) {
        if (userInfo == null) {
            return userInfo;
        }
        List<UserRole> userRoleList = userRoleService.findListByUserId(userInfo.getId());
        userInfo.setUserRoleList(userRoleList);
        return userInfo;
    }

}
