package cn.chenxubiao.picture.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.utils.ConstStrings;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.common.web.GuestBaseController;
import cn.chenxubiao.picture.domain.Attachment;
import cn.chenxubiao.picture.service.AttachmentService;
import cn.chenxubiao.picture.utils.DownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenxb on 17-4-20.
 */
@Controller
public class PictureShowController extends GuestBaseController {

    @Autowired
    private AttachmentService attachmentService;

    @RequestMapping(value = "/picture/show", method = RequestMethod.GET)
    public ResponseEntity showPicture(@RequestParam(value = "id", defaultValue = "0") int id,
                                          HttpServletResponse response) {
        if (id <= 0) {
            return null;
        }

        Attachment attachment = attachmentService.findById(id);
        if (attachment == null) {
            return null;
        }
        String relativePath = BBSConsts.PROTECTED_PIC_DISPOSE_PTTH + attachment.getRelativePath();
        response.setContentType(ConstStrings.CONTENT_TYPE_IMAGE);
        response.setCharacterEncoding(ConstStrings.CHARACTER_ENCOING_UTF8);
        DownloadUtil.downloadPicture(response, relativePath);
        return null;
    }
}
