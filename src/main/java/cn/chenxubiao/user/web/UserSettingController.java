package cn.chenxubiao.user.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.NumberUtil;
import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.common.utils.TimeUtil;
import cn.chenxubiao.common.utils.consts.BBSMapping;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.picture.service.AttachmentService;
import cn.chenxubiao.tag.service.TagCategoryService;
import cn.chenxubiao.user.bean.UserProfileBean;
import cn.chenxubiao.user.domain.UserHobby;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.service.UserHobbyService;
import cn.chenxubiao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    /**
     * 用户信息完善接口
     */
    @RequestMapping(value = "/user/profile/add", method = RequestMethod.POST)
    public ResponseEntity userProfile(HttpServletRequest request, UserProfileBean userProfile) {

        if (userProfile == null
                || !BBSMapping.USER_SEX_MAPPING.keySet().contains(userProfile.getSex())) {

            return ResponseEntity.failure(Errors.PARAMETER_ILLEGAL);
        }
        UserSession userSession = getUserSession(request);
        UserInfo userInfo = userInfoService.findById(userSession.getUserId());
        boolean isUserInfoModify = false;
        int avatarId = userProfile.getAvatarId();
        if (avatarId > 0) {
            boolean isAvatarIdExist = attachmentService.isPictureExist(avatarId);
            if (isAvatarIdExist) {
                userInfo.setAvatarId(avatarId);
                isUserInfoModify = true;
            }
        }
        if (userInfo.getSex() != userProfile.getSex()) {
            userInfo.setSex(userProfile.getSex());
            isUserInfoModify = true;
        }
        if (StringUtil.isNotBlank(userProfile.getBirthday())) {
            Date birthday = TimeUtil.parse(userProfile.getBirthday());
            userInfo.setBirthday(birthday);
            isUserInfoModify = true;
        }
        if (isUserInfoModify) {
            userInfo.setModifyTime(new Date());
            userInfoService.save(userInfo);
        }

        String categoryIds = userProfile.getCategoryIds();
        if (StringUtil.isNotBlank(categoryIds)) {
            Set<Integer> categoryIdSet = NumberUtil.parseToIntSet(categoryIds);
            if (CollectionUtil.isNotEmpty(categoryIdSet)) {
                List<UserHobby> userHobbyList = new ArrayList<>();
                for (int id : categoryIdSet) {
                    if (id <= 0) {
                        continue;
                    }
                    boolean isExist = tagCategoryService.isExist(id);
                    if (!isExist) {
                        continue;
                    }
                    UserHobby userHobby = new UserHobby();
                    userHobby.setCategoryId(id);
                    userHobby.setUserId(userInfo.getId());
                    userHobby.setCreateTime(new Date());
                    userHobby.setModifyTime(userHobby.getCreateTime());
                    userHobbyList.add(userHobby);
                }
                if (CollectionUtil.isNotEmpty(userHobbyList)) {
                    userHobbyService.saveAll(userHobbyList);
                }
            }
        }
        return ResponseEntity.success();
    }
}
