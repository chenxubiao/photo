package cn.chenxubiao.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenxb on 17-3-7.
 */
public class ConstStrings {
    public static final String REGX_EMAIL = "^[a-zA-Z0-9_\\+\\-\\.]+(\\.[a-zA-Z0-9_\\+\\-]+)*@[a-zA-Z0-9]+(\\.?[a-zA-Z0-9\\-]+)*\\.([a-zA-Z]{2,4})$";
    public static final String REGX_CELLPHONE = "^1[34578]\\d{9}$";
    public static final Map<String, String> HTML_ENTITY_MAP = new HashMap();
    //bmp、jpg、tiff、gif、pcx、tga、exif、fpx、svg、psd、cdr、pcd、dxf、ufo、eps、ai、raw
    public static final String PICTURE_JPG = "jpg";
    public static final String PICTURE_PNG = "png";
    public static final String PICTURE_JPEG = "jpeg";
    public static final String PICTURE_BMP = "bmp";
    public static final String PICTURE_GIF = "gif";

    public static final String DATE_PATTERN = "yyyy/MM/dd";

    public static final String CONTENT_TYPE_IMAGE = "image/jpeg";
    public static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";
    public static final String CONTENT_TYPE_DOWNLOAD = "application/octet-stream;charset=UTF-8";
    public static final String CHARACTER_ENCOING_UTF8 = "UTF-8";
}
