package cn.chenxubiao.picture.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.picture.domain.PictureAttachment;
import cn.chenxubiao.picture.service.PictureAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static cn.chenxubiao.common.utils.consts.BBSConsts.PROTECTED_BASE_PATH;

/**
 * Created by chenxb on 17-4-20.
 */
@Controller
public class DownloadController extends CommonController {

    @Autowired
    private PictureAttachmentService pictureAttachmentService;

    @RequestMapping(value = "/picture/download", method = RequestMethod.GET)
    public ResponseEntity downloadPicture(@RequestParam(value = "id", defaultValue = "0") int id,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        if (id <= 0) {
            System.out.println("download picture id == 0");
            return null;
        }
        PictureAttachment attachment = pictureAttachmentService.findById(id);
//        if (attachment == null) {
//            System.out.println("download picture attachment == null");
//            return null;
//        }
//        String relativePath = attachment.getRelativePath();
        FileInputStream inputStream;
//        response.setContentType("application/octet-stream;charset=UTF-8");//图片下载
        response.setContentType("image/jpeg");
        response.setCharacterEncoding("utf-8");
        try {
            //读图片
//            inputStream = new FileInputStream(PROTECTED_BASE_PATH + relativePath);
            inputStream = new FileInputStream("/var/upload/bbs/ali.jpg");
            int i = inputStream.available();
            byte[] data = new byte[i];
            inputStream.read(data);
            inputStream.close();

            //写图片
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
