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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.chenxubiao.common.utils.consts.BBSConsts.PROTECTED_BASE_PATH;

/**
 * Created by chenxb on 17-4-2.
 */
@RestController
public class UploadPictureController extends CommonController {

    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private PictureExifService pictureExifService;

    private List<String> valiadExtensions = BBSMapping.VALID_EXTENSIONS;

    @RequestMapping(value = "/upload/picture", method = RequestMethod.POST)
    public ResponseEntity uploadPicture(HttpServletRequest request, HttpSession session) {

        Map<String, Object> map = UploadUtil.uploadPicture(request);
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
        UserSession userSession = (UserSession) session.getAttribute(BBSConsts.USER_SESSION_KEY);
        int userId = userSession == null ? 0 : userSession.getUserId();
        attachment.setUserId(userId);
        attachmentService.save(attachment);

        //todo 启用多线程，保存图片exif信息
        Thread thread = new ExifThread(userId, attachment);
        thread.start();

//        if (attachment.getExt().equalsIgnoreCase(ConstStrings.PICTURE_JPEG)
//                || attachment.getExt().equalsIgnoreCase(ConstStrings.PICTURE_JPG)) {
//            File file = new File("/var/upload/bbs/me.jpg");
////        File picture = new File(PROTECTED_BASE_PATH + attachment.getRelativePath());
//            PictureExif pictureExif = ExifUtil.getExifInfo(file);
//            if (pictureExif != null) {
//                UserSession userSession = (UserSession) session.getAttribute(BBSConsts.USER_SESSION_KEY);
//                pictureExif.setUserId((userSession.getUserId()));
//                pictureExif.setCreateTime(new Date());
//                pictureExif.setModifyTime(pictureExif.getCreateTime());
//                pictureExif.setAttachmentId(attachment.getId());
//                pictureExifService.save(pictureExif);
//            }
//        }

        return ResponseEntity.success().set(BBSConsts.DATA, attachment);


//        if (request instanceof MultipartHttpServletRequest) {
//            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//            if (CollectionUtil.isEmpty(fileMap)) {
//                return ResponseEntity.failure(Errors.UNKNOWN_ERROR);
//            }
//            MultipartFile multipartFile = fileMap.get(BBSConsts.UPLOAD_NAME);
//            if (multipartFile == null || multipartFile.isEmpty()) {
//                return ResponseEntity.failure(Errors.UNKNOWN_ERROR);
//            }
//            String fileName = WebUtil.escapeHtml(getfileNameNoExt(multipartFile.getOriginalFilename()));
//            if (StringUtil.isNotBlank(fileName)) {
//                if (fileName.length() > BBSConsts.PICTURE_UPLOAD_NAME_LEN) {
//                    fileName = fileName.substring(0, BBSConsts.PICTURE_UPLOAD_NAME_LEN);
//                }
//            } else {
//                return ResponseEntity.failure(Errors.FILE_NAME_ERROR);
//            }
//            String ext = FileUtil.getExtension(multipartFile.getOriginalFilename());
//            if (StringUtil.isEmpty(ext) || !valiadExtensions.contains(ext.toLowerCase())) {
//                return ResponseEntity.failure(Errors.FILE_EXT_TYPE_ERROR + ext);
//            }
//            long length = multipartFile.getSize();
//            if (length > BBSConsts.PICTURE_UPLOAD_MAX_SIZE) {
//                return ResponseEntity.failure(Errors.FILE_LENGTH_TOO_LOGN);
//            }
//            Attachment attachment = new Attachment();
//            attachment.setFileName(fileName);
//            attachment.setExt(ext);
//            String filePath = new SimpleDateFormat(ConstStrings.DATE_PATTERN).format(new Date());
//            String uid = String.valueOf(NumberUtil.random(8));
//            attachment.setFilePath(filePath);
//            attachment.setUid(uid);
//            attachment.setCreateTime(new Date());
//            attachment.setModifyTime(attachment.getCreateTime());
//            attachment.setLength(length);
//            File baseFile = new File(PROTECTED_BASE_PATH + attachment.getRelativePath());
//            FileUtil.createDirs(baseFile);
//            try {
//                multipartFile.transferTo(baseFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return ResponseEntity.failure(Errors.UNKNOWN_ERROR);
//            }
//            boolean isImage = ImageUtil.checkImage(baseFile.toPath());
//            if (!isImage) {
//                baseFile.delete();
//                return ResponseEntity.failure(Errors.FILE_TYPE_ERROR);
//            }
//            attachmentService.save(attachment);
//            return ResponseEntity.success().set(BBSConsts.DATA, attachment);
//        }
//        return ResponseEntity.failure(Errors.FILE_TYPE_ERROR);
    }

    private String getfileNameNoExt(String fileName) {
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf('.');
            if ((dot > -1) && (dot < (fileName.length()))) {
                return fileName.substring(0, dot);
            }
        }
        return fileName;
    }

    public class ExifThread extends Thread {

        private int userId;
        private Attachment attachment;

        public ExifThread(int userId, Attachment attachment) {
            this.userId = userId;
            this.attachment = attachment;
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
            if (pictureExif != null) {
                pictureExif.setCreateTime(new Date());
                pictureExif.setModifyTime(pictureExif.getCreateTime());
                pictureExif.setPicId(attachment.getId());
                pictureExifService.save(pictureExif);
            }
        }
    }

}

