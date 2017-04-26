package cn.chenxubiao.picture.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.utils.*;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.BBSMapping;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.picture.domain.PictureAttachment;
import cn.chenxubiao.picture.service.PictureAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.chenxubiao.common.utils.consts.BBSConsts.PROTECTED_BASE_PATH;

/**
 * Created by chenxb on 17-4-2.
 */
@RestController
public class UploadController extends CommonController {

    @Autowired
    private PictureAttachmentService attachmentService;

    private List<String> valiadExtensions = BBSMapping.VALID_EXTENSIONS;

    @RequestMapping(value = "/picture/upload", method = RequestMethod.POST)
    public ResponseEntity uploadPicture(HttpServletRequest request) {

        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            if (CollectionUtil.isEmpty(fileMap)) {
                return ResponseEntity.failure(Errors.UNKNOWN_ERROR);
            }
            MultipartFile multipartFile = fileMap.get(BBSConsts.UPLOAD_NAME);
            if (multipartFile == null || multipartFile.isEmpty()) {
                return ResponseEntity.failure(Errors.UNKNOWN_ERROR);
            }
            String fileName = WebUtil.escapeHtml(getfileNameNoExt(multipartFile.getOriginalFilename()));
            if (StringUtil.isNotBlank(fileName)) {
                if (fileName.length() > BBSConsts.PICTURE_UPLOAD_NAME_LEN) {
                    fileName = fileName.substring(0, BBSConsts.PICTURE_UPLOAD_NAME_LEN);
                }
            } else {
                return ResponseEntity.failure(Errors.FILE_NAME_ERROR);
            }
            String ext = FileUtil.getExtension(multipartFile.getOriginalFilename());
            if (StringUtil.isEmpty(ext) || !valiadExtensions.contains(ext.toLowerCase())) {
                return ResponseEntity.failure(Errors.FILE_EXT_TYPE_ERROR + ext);
            }
            long length = multipartFile.getSize();
            if (length > BBSConsts.PICTURE_UPLOAD_MAX_SIZE) {
                return ResponseEntity.failure(Errors.FILE_LENGTH_TOO_LOGN);
            }
            PictureAttachment pictureAttachment = new PictureAttachment();
            pictureAttachment.setFileName(fileName);
            pictureAttachment.setExt(ext);
            String filePath = new SimpleDateFormat(ConstStrings.DATE_PATTERN).format(new Date());
            String uid = String.valueOf(NumberUtil.random(8));
            pictureAttachment.setFilePath(filePath);
            pictureAttachment.setUid(uid);
            pictureAttachment.setCreateTime(new Date());
            pictureAttachment.setModifyTime(pictureAttachment.getCreateTime());
            pictureAttachment.setLength(length);
            File baseFile = new File(PROTECTED_BASE_PATH + pictureAttachment.getRelativePath());
            FileUtil.createDirs(baseFile);
            try {
                multipartFile.transferTo(baseFile);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.failure(Errors.UNKNOWN_ERROR);
            }
            boolean isImage = ImageUtil.checkImage(baseFile.toPath());
            if (!isImage) {
                baseFile.delete();
                return ResponseEntity.failure(Errors.FILE_TYPE_ERROR);
            }
            attachmentService.save(pictureAttachment);
            return ResponseEntity.success().set("data", pictureAttachment);
        }
        return ResponseEntity.failure(Errors.FILE_TYPE_ERROR);
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
}
