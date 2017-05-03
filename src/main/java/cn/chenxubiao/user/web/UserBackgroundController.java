package cn.chenxubiao.user.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.picture.domain.Attachment;
import cn.chenxubiao.picture.service.AttachmentService;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenxb on 17-5-1.
 */
@RestController
public class UserBackgroundController extends CommonController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AttachmentService attachmentService;
    @RequestMapping(value = "/user/background/update", method = RequestMethod.POST)
    public ResponseEntity uploadUserBackground(@RequestParam(value = "picId", defaultValue = "0") int picId,
                                           HttpServletRequest request) {

        if (picId <= 0) {
            return ResponseEntity.failure(Errors.PARAMETER_ILLEGAL);
        }
        Attachment attachment = attachmentService.findById(picId);
        if (attachment == null) {
            return ResponseEntity.failure(Errors.PICTURE_NOT_FOUND);
        }
        UserSession userSession = getUserSession(request);
        UserInfo userInfo = userInfoService.findById(userSession.getUserId());
        userInfo.setBackgroundId(picId);
        userInfoService.save(userInfo);
        userSession = buildUserSession(userInfo);
        setUserSession(request, userSession);
        return ResponseEntity.success();
    }
}
