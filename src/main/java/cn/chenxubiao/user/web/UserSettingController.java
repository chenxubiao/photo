package cn.chenxubiao.user.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.*;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.BBSMapping;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.picture.service.AttachmentService;
import cn.chenxubiao.tag.service.TagCategoryService;
import cn.chenxubiao.user.bean.UserProfileBean;
import cn.chenxubiao.user.domain.UserHobby;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.domain.UserTool;
import cn.chenxubiao.user.service.UserHobbyService;
import cn.chenxubiao.user.service.UserInfoService;
import cn.chenxubiao.user.service.UserToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by chenxb on 17-5-1.
 */
@RestController
public class UserSettingController extends CommonController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private TagCategoryService tagCategoryService;
    @Autowired
    private UserHobbyService userHobbyService;
    @Autowired
    private UserToolService userToolService;

    /**
     * 用户信息完善接口
     */
    @RequestMapping(value = "/user/profile/update", method = RequestMethod.POST)
    public ResponseEntity userProfile(HttpServletRequest request, UserProfileBean userProfile) {

        if (userProfile == null
                || !BBSMapping.USER_SEX_MAPPING.keySet().contains(userProfile.getSex())) {

            return ResponseEntity.failure(Errors.PARAMETER_ILLEGAL);
        }
        UserSession userSession = getUserSession(request);
        UserInfo userInfo = userInfoService.findById(userSession.getUserId());
        boolean isUserInfoModify = false;
        int avatarId = userProfile.getAvatarId();

        if (StringUtil.isBlank(userProfile.getUserName())) {
            return ResponseEntity.failure(Errors.USER_USERNAME_NULL);
        }
        String userName = userProfile.getUserName().trim();
        if (!userInfo.getUserName().equals(userName)) {
            isUserInfoModify = true;
            boolean isUserNameExist = userInfoService.isUserNameExist(userName);
            if (isUserNameExist) {
                return ResponseEntity.failure(Errors.USER_USERNAME_IS_EXISTS);
            }
            userInfo.setUserName(userProfile.getUserName().trim());
        }
        if (avatarId > 0 && avatarId != userInfo.getAvatarId()) {
            boolean isAvatarIdExist = attachmentService.isPictureExist(avatarId);
            if (isAvatarIdExist) {
                userInfo.setAvatarId(avatarId);
                isUserInfoModify = true;
            }
        }
        if (StringUtil.isNotBlank(userProfile.getDescription())
                && !userProfile.getDescription().trim().equals(userInfo.getDescription())) {
            userInfo.setDescription(userProfile.getDescription().trim());
            isUserInfoModify = true;
        }
        if (userInfo.getSex() != userProfile.getSex()) {
            userInfo.setSex(userProfile.getSex());
            isUserInfoModify = true;
        }
        if (userInfo.getBirthday() != null) {
            if (userInfo.getBirthday().compareTo(userProfile.getBirthday()) != 0) {
                userInfo.setBirthday(userProfile.getBirthday());
                isUserInfoModify = true;
            }
        }

        if (isUserInfoModify) {
            userInfo.setModifyTime(new Date());
            userInfoService.save(userInfo);

            userSession = super.buildUserSession(userInfo);
            super.setUserSession(request, userSession);
        }


        String categoryIds = userProfile.getCategoryIds();
        if (StringUtil.isNotBlank(categoryIds)) {
            Set<Integer> categoryIdSet = NumberUtil.parseToIntSet(categoryIds);
            if (CollectionUtil.isNotEmpty(categoryIdSet)) {

                List<UserHobby> userHobbyList = new ArrayList<>();
                List<UserHobby> userHobbyListDB = userHobbyService.findByUserId(userInfo.getId());

                if (CollectionUtil.isEmpty(userHobbyListDB)) {
                    getUserHobbyList(userHobbyList, userInfo.getId(), categoryIdSet);
                } else {
                    Set<Integer> categoryIdDB = new HashSet<>();
                    for (UserHobby userHobby : userHobbyListDB) {
                        categoryIdDB.add(userHobby.getCategoryId());
                    }
                    //求交集   保留，其余删除
                    categoryIdDB.retainAll(categoryIdSet);
                    userHobbyService.deleteNotInCategoryIds(userInfo.getId(), new ArrayList<>(categoryIdDB));
                    categoryIdSet.removeAll(categoryIdDB);
                    if (CollectionUtil.isNotEmpty(categoryIdSet)) {
                        getUserHobbyList(userHobbyList, userInfo.getId(), categoryIdSet);
                    }
                }

                if (CollectionUtil.isNotEmpty(userHobbyList)) {
                    userHobbyService.saveAll(userHobbyList);
                }
            }
        }

        List<UserTool> userToolList = userToolService.findByUserId(userInfo.getId());
        Set<String> cameraDB = new HashSet<>();
        Set<String> lenDB = new HashSet<>();
        Set<String> toolDB = new HashSet<>();
        if (CollectionUtil.isNotEmpty(userToolList)) {
            for (UserTool userTool : userToolList) {
                if (userTool.getType() == BBSConsts.UserToolType.CAMERA) {
                    cameraDB.add(userTool.getName());
                } else if (userTool.getType() == BBSConsts.UserToolType.LEN) {
                    lenDB.add(userTool.getName());
                } else if (userTool.getType() == BBSConsts.UserToolType.TOOL) {
                    toolDB.add(userTool.getName());
                }
            }
        }

        List<UserTool> userToolListToSave = new ArrayList<>();
        if (StringUtil.isNotBlank(userProfile.getCameraNames())) {

            String[] strings = userProfile.getCameraNames().split(",|，");
            if (strings.length > 0) {
                Set<String> cameraList = new HashSet<>();
                for (String string : strings) {
                    if (StringUtil.isNotBlank(string)) {
                        string = string.trim();
                        cameraList.add(string);
                    }
                }
                if (CollectionUtil.isNotEmpty(cameraList)) {
                    if (CollectionUtil.isEmpty(cameraDB)) {
                        for (String camera : cameraList) {
                            userToolListToSave.add(getUserTool(camera, userInfo.getId(), BBSConsts.UserToolType.CAMERA));
                        }
                    } else {
                        cameraDB.retainAll(cameraList);//DB为交集，数据库保存,其他删除
                        if (CollectionUtil.isEmpty(cameraDB)) {
                            userToolService.deleteAllByType(userInfo.getId(), BBSConsts.UserToolType.CAMERA);
                        } else {
                            userToolService.deleteNotInNameList
                                    (userInfo.getId(), BBSConsts.UserToolType.CAMERA, new ArrayList<>(cameraDB));
                        }
                        cameraList.removeAll(cameraDB);
                        if (CollectionUtil.isNotEmpty(cameraList)) {
                            for (String camera : cameraList) {
                                userToolListToSave.add(getUserTool(camera, userInfo.getId(), BBSConsts.UserToolType.CAMERA));
                            }
                        }
                    }
                }
            }
        }


        if (StringUtil.isNotBlank(userProfile.getLensNames())) {

            String[] strings = userProfile.getLensNames().split(",|，");
            if (strings.length > 0) {
                Set<String> lenList = new HashSet<>();

                for (String string : strings) {
                    if (StringUtil.isNotBlank(string)) {
                        string = string.trim();
                        lenList.add(string);
                    }
                }
                if (CollectionUtil.isNotEmpty(lenList)) {
                    if (CollectionUtil.isEmpty(lenDB)) {
                        for (String len : lenList) {
                            userToolListToSave.add(getUserTool(len, userInfo.getId(), BBSConsts.UserToolType.LEN));
                        }
                    } else {
                        lenDB.retainAll(lenList);//DB为交集，数据库保存,其他删除
                        if (CollectionUtil.isEmpty(lenDB)) {
                            userToolService.deleteAllByType(userInfo.getId(), BBSConsts.UserToolType.LEN);
                        } else {
                            userToolService.deleteNotInNameList
                                    (userInfo.getId(), BBSConsts.UserToolType.LEN, new ArrayList<>(lenDB));
                        }
                        lenList.removeAll(lenDB);
                        if (CollectionUtil.isNotEmpty(lenList)) {
                            for (String len : lenList) {
                                userToolListToSave.add(getUserTool(len, userInfo.getId(), BBSConsts.UserToolType.LEN));
                            }
                        }
                    }
                }
            }
        }

        if (StringUtil.isNotBlank(userProfile.getToolNames())) {

            String[] strings = userProfile.getToolNames().split(",|，");
            if (strings.length > 0) {
                Set<String> toolList = new HashSet<>();

                for (String string : strings) {
                    if (StringUtil.isNotBlank(string)) {
                        string = string.trim();
                        toolList.add(string);
                    }
                }
                if (CollectionUtil.isNotEmpty(toolList)) {
                    if (CollectionUtil.isEmpty(toolDB)) {
                        for (String tool : toolList) {
                            userToolListToSave.add(getUserTool(tool, userInfo.getId(), BBSConsts.UserToolType.TOOL));
                        }
                    } else {
                        toolDB.retainAll(toolList);//DB为交集，数据库保存,其他删除
                        if (CollectionUtil.isEmpty(toolDB)) {
                            userToolService.deleteAllByType(userInfo.getId(), BBSConsts.UserToolType.TOOL);
                        } else {
                            userToolService.deleteNotInNameList
                                    (userInfo.getId(), BBSConsts.UserToolType.TOOL, new ArrayList<>(toolDB));
                        }
                        toolList.removeAll(toolDB);
                        if (CollectionUtil.isNotEmpty(toolList)) {
                            for (String tool : toolList) {
                                userToolListToSave.add(getUserTool(tool, userInfo.getId(), BBSConsts.UserToolType.TOOL));
                            }
                        }
                    }
                }
            }
        }

        if (CollectionUtil.isNotEmpty(userToolListToSave)) {
            userToolService.saveAll(userToolListToSave);
        }

        return ResponseEntity.success();
    }

    private UserTool getUserTool(String name, int userId, int type) {
        UserTool userTool = new UserTool(name, userId, type);
        userTool.setCreateTime(new Date());
        userTool.setModifyTime(userTool.getCreateTime());
        return userTool;
    }

    private List<UserHobby> getUserHobbyList(List<UserHobby> userHobbyList, int userId, Set<Integer> ids) {

        for (int id : ids) {
            if (id <= 0) {
                continue;
            }
            boolean isExist = tagCategoryService.isExist(id);
            if (!isExist) {
                continue;
            }
            UserHobby userHobby = new UserHobby();
            userHobby.setCategoryId(id);
            userHobby.setUserId(userId);
            userHobby.setCreateTime(new Date());
            userHobby.setModifyTime(userHobby.getCreateTime());
            userHobbyList.add(userHobby);
        }
        return userHobbyList;
    }

    @RequestMapping(value = "/user/profile/passsword/update", method = RequestMethod.POST)
    public ResponseEntity updatePassword(HttpServletRequest request,
                                         @RequestParam(name = "oldPasswd", value = "") String oldPasswd,
                                         @RequestParam(name = "newPasswd", value = "") String newPasswd) {

        if (!super.isPasswordGood(oldPasswd) || !super.isPasswordGood(newPasswd)) {
            return ResponseEntity.failure(Errors.PASSWORD_LENGTH_ERROR);
        }
        oldPasswd = HashUtil.encrypt(oldPasswd.trim());

        UserSession userSession = super.getUserSession(request);
        UserInfo userInfo = userInfoService.findById(userSession.getUserId());
        if (!oldPasswd.equals(userInfo.getPassword())) {
            return ResponseEntity.failure(Errors.PASSWORD_OLD_ERROR);
        }
        newPasswd = HashUtil.encrypt(newPasswd.trim());
        if (newPasswd.equals(userInfo.getPassword())) {
            return ResponseEntity.failure(Errors.PASSWOED_OLD_EQUAL_NEW);
        }
        userInfo.setPassword(newPasswd);
        userInfoService.save(userInfo);
        return ResponseEntity.success();
    }

}
