package cn.chenxubiao.picture.web;

import cn.chenxubiao.common.utils.StringUtil;
import cn.chenxubiao.common.utils.TimeUtil;
import cn.chenxubiao.picture.domain.PictureExif;
import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.jpeg.JpegMetadataReader;

import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifInteropDirectory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.ExifThumbnailDirectory;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import static cn.chenxubiao.common.utils.consts.BBSConsts.PROTECTED_BASE_PATH;

/**
 * Created by chenxb on 17-4-27.
 */
public class PictureTest {

    public static void test() {

        File file = new File("/var/upload/bbs/me.jpg");
//        File picture = new File(PROTECTED_BASE_PATH + attachment.getRelativePath());
        Metadata metadata = null;
        try {
            metadata = JpegMetadataReader.readMetadata(file);
        } catch (JpegProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PictureExif pictureExif = new PictureExif();
        Directory exif = metadata.getDirectory(ExifIFD0Directory.class);
        if (exif != null) {
            if (exif.containsTag(ExifIFD0Directory.TAG_MODEL)) {
                pictureExif.setCamera(exif.getDescription(ExifIFD0Directory.TAG_MODEL));
                System.out.println("camera:" + exif.getDescription(ExifIFD0Directory.TAG_MODEL));
            }
        }

        Directory exif2 = metadata.getDirectory(ExifSubIFDDirectory.class);
        if (exif2 != null) {
            if (exif2.containsTag(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)) {
                String dateString = exif2.getDescription(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
//                if (StringUtil.isNotBlank(dateString)) {
//                    pictureExif.setTaken(dateString);
//                }
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
            if(exif2.containsTag(ExifSubIFDDirectory.TAG_EXIF_IMAGE_WIDTH)){
                String widthString = exif2.getDescription(ExifSubIFDDirectory.TAG_EXIF_IMAGE_WIDTH);
                int width = 0;
                if (StringUtil.isNotEmpty(widthString) && "null".equalsIgnoreCase(widthString)) {
                    width = Integer.parseInt(widthString.replaceAll("[^0-9]", ""));
                }
                pictureExif.setWidth(width);
                System.out.println("width:" + exif2.getDescription(ExifSubIFDDirectory.TAG_EXIF_IMAGE_WIDTH));
            }
            if(exif2.containsTag(ExifSubIFDDirectory.TAG_EXIF_IMAGE_HEIGHT)){
                String heightString = exif2.getDescription(ExifSubIFDDirectory.TAG_EXIF_IMAGE_HEIGHT);
                int height = 0;
                if (StringUtil.isNotEmpty(heightString) && "null".equalsIgnoreCase(heightString)) {
                    height = Integer.parseInt(heightString.replaceAll("[^0-9]", ""));
                }
                pictureExif.setHeight(height);
                System.out.println("height:" + exif2.getDescription(ExifSubIFDDirectory.TAG_EXIF_IMAGE_HEIGHT));
            }
        }
    }
    public static void main(String[] args) {
        test();
//        try {
//            File file = new File("/var/upload/bbs/me.jpg");
//
//            Metadata metadata = JpegMetadataReader.readMetadata(file);
//
//            Directory exif = metadata.getDirectory(ExifIFD0Directory.class);
//            if(null != exif){
//                Iterator<Tag> iterator = exif.getTags().iterator();
//                while(iterator.hasNext()){
//                    Tag tag = iterator.next();
//                    System.out.println(tag);
//                }
//            }
//            if(exif.containsTag(ExifIFD0Directory.TAG_MAKE)){
//                System.out.println("Make:" + exif.getDescription(ExifIFD0Directory.TAG_MAKE));
//            }
//
//            System.out.println("-----------------------1---------------------");
//            Directory exif2 = metadata.getDirectory(ExifSubIFDDirectory.class);
//            if(null != exif2){
//                Iterator<Tag> iterator2 = exif2.getTags().iterator();
//                while(iterator2.hasNext()){
//                    Tag tag2 = iterator2.next();
//
//                    System.out.println(tag2);
//                }
//            }
//
//            System.out.println("-----------------------2---------------------");
//            Directory exif3 = metadata.getDirectory(ExifInteropDirectory.class);
//            if(null != exif3){
//                Iterator<Tag> iterator3 = exif3.getTags().iterator();
//                while(iterator3.hasNext()){
//                    Tag tag3 = iterator3.next();
//                    System.out.println(tag3);
//                }
//            }
//
//            System.out.println("------------------------3--------------------");
//            Directory exif4 = metadata.getDirectory(ExifThumbnailDirectory.class);
//            if(null != exif4){
//                Iterator<Tag> iterator4 = exif4.getTags().iterator();
//                while(iterator4.hasNext()){
//                    Tag tag4 = iterator4.next();
//                    System.out.println(tag4);
//                }
//            }
//        } catch (ImageProcessingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
