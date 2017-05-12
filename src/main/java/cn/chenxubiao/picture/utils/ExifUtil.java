package cn.chenxubiao.picture.utils;

import cn.chenxubiao.picture.domain.PictureExif;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenxb on 17-4-27.
 */
public class ExifUtil {
    public static PictureExif getExifInfo(File file) {
        Metadata metadata = null;
        try {
            metadata = JpegMetadataReader.readMetadata(file);
        } catch (JpegProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PictureExif pictureExif = null;
        Directory exif = metadata.getDirectory(ExifIFD0Directory.class);
        if (exif != null) {
            if (exif.containsTag(ExifIFD0Directory.TAG_MODEL)) {
                pictureExif = new PictureExif();
                pictureExif.setCamera(exif.getDescription(ExifIFD0Directory.TAG_MODEL));
                System.out.println("camera:" + exif.getDescription(ExifIFD0Directory.TAG_MAKE));
            }
        }

        Directory exif2 = metadata.getDirectory(ExifSubIFDDirectory.class);
        if (exif2 != null) {
            if (pictureExif == null) {
                pictureExif = new PictureExif();
            }
            if (exif2.containsTag(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)) {
                pictureExif.setTaken(exif2.getDescription(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL));
                System.out.println("datetime:" + exif2.getDescription(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL));
            }
            if (exif2.containsTag(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT)) {
                pictureExif.setIso(exif2.getDescription(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT));
                System.out.println("iso:" + exif2.getDescription(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT));
            }
            if (exif2.containsTag(ExifSubIFDDirectory.TAG_APERTURE)) {
                pictureExif.setAperture(exif2.getDescription(ExifSubIFDDirectory.TAG_APERTURE));
                System.out.println("F:" + exif2.getDescription(ExifSubIFDDirectory.TAG_APERTURE));
            }
            if (exif2.containsTag(ExifSubIFDDirectory.TAG_SHUTTER_SPEED)) {
                pictureExif.setShutterSpeed(exif2.getDescription(ExifSubIFDDirectory.TAG_SHUTTER_SPEED));
                System.out.println("speed:" + exif2.getDescription(ExifSubIFDDirectory.TAG_SHUTTER_SPEED));
            }
            if (exif2.containsTag(ExifSubIFDDirectory.TAG_FOCAL_LENGTH)) {
                pictureExif.setFocalLength(exif2.getDescription(ExifSubIFDDirectory.TAG_FOCAL_LENGTH));
                System.out.println("jiao ju:" + exif2.getDescription(ExifSubIFDDirectory.TAG_FOCAL_LENGTH));
            }
            if (exif2.containsTag(ExifSubIFDDirectory.TAG_LENS_MODEL)) {
                pictureExif.setLens(exif2.getDescription(ExifSubIFDDirectory.TAG_LENS_MODEL));
                System.out.println("lens:" + exif2.getDescription(ExifSubIFDDirectory.TAG_LENS_MODEL));
            }
        }
        Map<String, Integer> size = getPicSize(file);
        Integer width = size.get("width");
        Integer height = size.get("height");
        if (width != null && width > 0) {
            pictureExif.setWidth(width);
        }
        if (height != null && height > 0) {
            pictureExif.setHeight(height);
        }
        return pictureExif;
    }

    public static Map<String, Integer> getPicSize(File file) {
        Image image = null;
        Map<String, Integer> map = new HashMap<>();
        try {
            image = javax.imageio.ImageIO.read(file);
            map.put("width", image.getWidth(null));
            map.put("height", image.getHeight(null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
