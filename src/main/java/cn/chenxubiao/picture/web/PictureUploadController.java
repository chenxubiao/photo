package cn.chenxubiao.picture.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.*;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.BBSMapping;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.picture.domain.Attachment;
import cn.chenxubiao.picture.domain.PictureExif;
import cn.chenxubiao.picture.service.AttachmentService;
import cn.chenxubiao.picture.service.PictureExifService;
import cn.chenxubiao.picture.utils.ExifUtil;
import cn.chenxubiao.picture.utils.UploadUtil;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.Map;

import static cn.chenxubiao.common.utils.consts.BBSConsts.PROTECTED_BASE_PATH;
import static cn.chenxubiao.common.utils.consts.BBSConsts.PROTECTED_PIC_DISPOSE_PTTH;

/**
 * Created by chenxb on 17-4-2.
 */
@RestController
public class PictureUploadController extends CommonController {

    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private PictureExifService pictureExifService;
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/picture/upload/project", method = RequestMethod.POST)
    public ResponseEntity uploadPicture(HttpServletRequest request) {

        Map<String, Object> map = UploadUtil.uploadPicture(request, BBSMapping.PICTURE_PROJECT_LIST);
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

        File file = new File(BBSConsts.PROTECTED_BASE_PATH + attachment.getRelativePath());
        Map<String, Integer> size = ExifUtil.getPicSize(file);
        Integer width = size.get("width");
        Integer height = size.get("height");
        if (width == null || height == null || width <= 60 || height <= 60) {
            return ResponseEntity.failure(Errors.PICTURE_SIZE_ERROR);
        }

        UserSession userSession = getUserSession(request);
        int userId = userSession.getUserId();
        attachment.setUserId(userId);
        attachmentService.save(attachment);
        UserInfo userInfo = userInfoService.findById(userId);

        String sourcePath = PROTECTED_BASE_PATH + attachment.getRelativePath();
        String destPath = PROTECTED_PIC_DISPOSE_PTTH + attachment.getRelativePath();
        int fontSize;
        int distence;
        if (width >= height) {
            fontSize = width / 45;
            distence = width / 60;
        } else {
            fontSize = height / 45;
            distence = height / 60;
        }
        try {

            Im4javaUtil.resize(sourcePath, destPath, width, height, 40d, false);
            String content = (BBSConsts.PICTURE_PREFIX + userInfo.getUserName());
            Im4javaUtil.addTextWatermark(destPath, destPath, content, fontSize, distence);
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //todo 启用多线程，保存图片exif信息
        Thread thread = new ExifThread(userInfo, attachment);
        thread.start();

        return ResponseEntity.success().set(BBSConsts.DATA, attachment);
    }

    public class ExifThread extends Thread {

        private int userId;
        private UserInfo userInfo;
        private Attachment attachment;

        public ExifThread(UserInfo userInfo, Attachment attachment) {
            this.userInfo = userInfo;
            this.attachment = attachment;
        }

        public UserInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
        }

        public Attachment getAttachment() {
            return attachment;
        }

        public void setAttachment(Attachment attachment) {
            this.attachment = attachment;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        @Override
        public void run() {
            File file = new File(PROTECTED_BASE_PATH + attachment.getRelativePath());
            PictureExif pictureExif = ExifUtil.getExifInfo(file);
            if (pictureExif == null) {
                return;
            }
            pictureExif.setCreateTime(new Date());
            pictureExif.setModifyTime(pictureExif.getCreateTime());
            pictureExif.setPicId(attachment.getId());
            pictureExifService.save(pictureExif);
        }
    }

}

