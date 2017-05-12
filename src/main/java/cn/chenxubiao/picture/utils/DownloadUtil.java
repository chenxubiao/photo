package cn.chenxubiao.picture.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import static cn.chenxubiao.common.utils.consts.BBSConsts.PROTECTED_BASE_PATH;

/**
 * Created by chenxb on 17-4-28.
 */
public class DownloadUtil {
    public static void downloadPicture(HttpServletResponse response, String relativePath) {

        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            //读图片
            inputStream = new FileInputStream(relativePath);
            int i = inputStream.available();
            byte[] data = new byte[i];
            inputStream.read(data);
            inputStream.close();

            //写图片
            outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
