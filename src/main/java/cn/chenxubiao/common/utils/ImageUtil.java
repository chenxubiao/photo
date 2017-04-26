package cn.chenxubiao.common.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Created by chenxb on 17-4-2.
 */
public class ImageUtil {
    public static boolean checkImage(Path path) {
        if(path == null) {
            return false;
        } else {
            String fileName = path.getFileName().toString();
            if(StringUtil.isNotEmpty(fileName) && fileName.endsWith("dcm")) {
                InputStream inputStream = null;
                BufferedInputStream bis = null;

                try {
                    inputStream = new FileInputStream(path.toFile());
                    bis = new BufferedInputStream(inputStream);
                    bis.mark(132);
                    byte[] a = new byte[132];
                    if(bis.read(a) == 132) {
                        bis.reset();
                        String test = new String(a, "UTF-8");
                        boolean var6 = "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000DICM".equals(test);
                        return var6;
                    }
                } catch (IOException var24) {
                    var24.printStackTrace();
                } finally {
                    if(bis != null) {
                        try {
                            bis.close();
                        } catch (IOException var22) {
                            var22.printStackTrace();
                        }
                    }

                    if(inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException var21) {
                            var21.printStackTrace();
                        }
                    }

                }
            }

            try {
                BufferedImage img = ImageIO.read(path.toUri().toURL());
                return img != null && img.getWidth((ImageObserver)null) > 0 && img.getHeight((ImageObserver)null) > 0;
            } catch (Exception var23) {
                return false;
            }
        }
    }
}
