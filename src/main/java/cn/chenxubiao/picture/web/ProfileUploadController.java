package cn.chenxubiao.picture.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.Im4javaUtil;
import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.BBSMapping;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.common.web.GuestBaseController;
import cn.chenxubiao.picture.domain.Attachment;
import cn.chenxubiao.picture.service.AttachmentService;
import cn.chenxubiao.picture.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import static cn.chenxubiao.common.utils.consts.BBSConsts.PROTECTED_BASE_PATH;
import static cn.chenxubiao.common.utils.consts.BBSConsts.PROTECTED_PIC_DISPOSE_PTTH;

/**
 * Created by chenxb on 17-4-27.
 */
@RestController
public class ProfileUploadController extends CommonController {

    @Autowired
    private AttachmentService attachmentService;

    /**
     * 头像、背景图片上传接口
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/picture/upload/profile", method = RequestMethod.POST)
    public ResponseEntity uploadUserProfilePicture(HttpServletRequest request) {

        Map<String, Object> map = UploadUtil.uploadPicture(request, BBSMapping.VALID_EXTENSIONS_LIST);
        if (CollectionUtil.isEmpty(map)) {
            return ResponseEntity.failure(Errors.UNKNOWN_ERROR);
        }
        String error = (String) map.get(BBSConsts.FALSE);
        if (StringUtil.isNotEmpty(error)) {
            return ResponseEntity.failure(error);
        }
        Attachment attachment = (Attachment) map.get(BBSConsts.TRUE);
        if (attachment == null) {
            return ResponseEntity.failure(Errors.UPLOAD_ERROR);
        }
        UserSession userSession = getUserSession(request);
        int userId = userSession == null ? 1 : userSession.getUserId();
        attachment.setUserId(userId);
        attachmentService.save(attachment);

        String sourcePath = PROTECTED_BASE_PATH + attachment.getRelativePath();
        String destPath = PROTECTED_PIC_DISPOSE_PTTH + attachment.getRelativePath();
        try {
            File file = new File(sourcePath);
            Image image = null;
            image = javax.imageio.ImageIO.read(file);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            Im4javaUtil.resize(sourcePath, destPath, width, height, 40d, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.success().set(BBSConsts.DATA, attachment);
    }
}
