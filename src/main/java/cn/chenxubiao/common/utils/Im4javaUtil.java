package cn.chenxubiao.common.utils;

import org.im4java.core.*;
import org.im4java.process.ArrayListOutputConsumer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by chenxb on 17-5-8.
 */
public class Im4javaUtil {
    /**
     * 是否使用 GraphicsMagick
     */
    private static final boolean USE_GRAPHICS_MAGICK_PATH = false;

    /**
     * ImageMagick安装路径
     */
    private static final String IMAGE_MAGICK_PATH = "D:\\software\\ImageMagick-6.2.7-Q8";

    /**
     * GraphicsMagick 安装目录
     */
    private static final String GRAPHICS_MAGICK_PATH = "D:\\software\\GraphicsMagick-1.3.23-Q8";

    /**
     * 水印图片路径
     */
    private static String watermarkImagePath = "/var/upload/bbs/a.png";

    /**
     * 水印图片
     */
    private static Image watermarkImage = null;

    private static String FONT_STRING = "Ubuntu";

    static {
        try {
            //watermarkImage = ImageIO.read(new URL(watermarkImagePath));
            watermarkImage = ImageIO.read(new File(watermarkImagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 命令类型
     *
     * @author hailin0@yeah.net
     * @createDate 2016年6月5日
     *
     */
    private enum CommandType {
        convert("转换处理"), identify("图片信息"), compositecmd("图片合成");
        private String name;

        CommandType(String name) {
            this.name = name;
        }
    }

    /**
     * 获取 ImageCommand
     *
     * @param command 命令类型
     * @return
     */
    private static ImageCommand getImageCommand(CommandType command) {
        ImageCommand cmd = null;
        switch (command) {
            case convert:
                cmd = new ConvertCmd(USE_GRAPHICS_MAGICK_PATH);
                break;
            case identify:
                cmd = new IdentifyCmd(USE_GRAPHICS_MAGICK_PATH);
                break;
            case compositecmd:
                cmd = new CompositeCmd(USE_GRAPHICS_MAGICK_PATH);
                break;
        }
        if (cmd != null && System.getProperty("os.name").toLowerCase().indexOf("windows") != -1) {
            cmd.setSearchPath(USE_GRAPHICS_MAGICK_PATH ? GRAPHICS_MAGICK_PATH : IMAGE_MAGICK_PATH);
        }
        return cmd;
    }

    /**
     * 获取图片信息
     *
     * @param srcImagePath 图片路径
     * @return Map {height=, filelength=, directory=, width=, filename=}
     */
    public static Map<String, Object> getImageInfo(String srcImagePath) {
        IMOperation op = new IMOperation();
        op.format("%w,%h,%d,%f,%b");
        op.addImage(srcImagePath);
        IdentifyCmd identifyCmd = (IdentifyCmd) getImageCommand(CommandType.identify);
        ArrayListOutputConsumer output = new ArrayListOutputConsumer();
        identifyCmd.setOutputConsumer(output);
        try {
            identifyCmd.run(op);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> cmdOutput = output.getOutput();
        if (cmdOutput.size() != 1)
            return null;
        String line = cmdOutput.get(0);
        String[] arr = line.split(",");
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("width", Integer.parseInt(arr[0]));
        info.put("height", Integer.parseInt(arr[1]));
        info.put("directory", arr[2]);
        info.put("filename", arr[3]);
        info.put("filelength", Integer.parseInt(arr[4].replace("B", "")));
        return info;
    }

    /**
     * 文字水印
     *
     * @param srcImagePath  源图片路径
     * @param destImagePath 目标图片路径
     * @param content       文字内容（不支持汉字）
     * @throws Exception
     */
    public static void addTextWatermark(String srcImagePath, String destImagePath, String content, int fontSize, int distance)
            throws Exception {

        IMOperation op = new IMOperation();
        op.font(FONT_STRING);
        // 文字方位-东南
        op.gravity("southeast");
        // 文字信息
        op.pointsize(fontSize).fill("#BCBFC8").draw("text " + distance + "," + distance + " " + content);
        // 原图
        op.addImage(srcImagePath);
        // 目标
        op.addImage(createDirectory(destImagePath));
        ImageCommand cmd = getImageCommand(CommandType.convert);
        cmd.run(op);
    }

    /**
     * 图片水印
     *
     * @param srcImagePath 源图片路径
     * @param destImagePath 目标图片路径
     * @param dissolve 透明度（0-100）
     * @throws Exception
     */
    public static void addImgWatermark(String srcImagePath, String destImagePath, Integer dissolve)
            throws Exception {
        // 原始图片信息
        BufferedImage buffimg = ImageIO.read(new File(srcImagePath));
        int w = buffimg.getWidth();
        int h = buffimg.getHeight();

        IMOperation op = new IMOperation();
        // 水印图片位置
        op.geometry(watermarkImage.getWidth(null), watermarkImage.getHeight(null), w
                - watermarkImage.getWidth(null) - 10, h - watermarkImage.getHeight(null) - 10);
        // 水印透明度
        op.dissolve(dissolve);
        // 水印
        op.addImage(watermarkImagePath);
        // 原图
        op.addImage(srcImagePath);
        // 目标
        op.addImage(createDirectory(destImagePath));
        ImageCommand cmd = getImageCommand(CommandType.compositecmd);
        cmd.run(op);
    }

    /**
     * jdk压缩图片-质量压缩
     *
     * @param destImagePath 被压缩文件路径
     * @param quality 压缩质量比例
     * @return
     * @throws Exception
     */
    public static void jdkResize(String destImagePath, float quality) throws Exception {
//        // 目标文件
//        File resizedFile = new File(destImagePath);
//        // 压缩
//        Image targetImage = ImageIO.read(resizedFile);
//        int width = targetImage.getWidth(null);
//        int height = targetImage.getHeight(null);
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        Graphics g = image.createGraphics();
//        g.drawImage(targetImage, 0, 0, width, height, null);
//        g.dispose();
//
//        String ext = getFileType(resizedFile.getName());
//        if (ext.equals("jpg") || ext.equals("jpeg")) { // 如果是jpg
//            // jpeg格式的对输出质量进行设置
//            FileOutputStream out = new FileOutputStream(resizedFile);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(image);
//            jep.setQuality(quality, false);
//            encoder.setJPEGEncodeParam(jep);
//            encoder.encode(image);
//            out.close();
//        } else {
//            ImageIO.write(image, ext, resizedFile);
//        }

    }

    /**
     * 压缩图片
     *
     * @param srcImagePath 源图片路径
     * @param destImagePath 目标图片路径
     * @param width 压缩后的宽
     * @param height 压缩后的高
     * @param quality 压缩质量（0-100）
     * @param needWatermark 是否加水印
     * @return
     * @throws Exception
     */
    public static void resize(String srcImagePath, String destImagePath, int width, int height,
                              Double quality, boolean needWatermark) throws Exception {
        // 按照原有形状压缩（横图、竖图）
        BufferedImage buffimg = ImageIO.read(new File(srcImagePath));
        int w = buffimg.getWidth();
        int h = buffimg.getHeight();
        if ((w > h && width < height) || (w < h && width > height)) {
            int temp = width;
            width = height;
            height = temp;
        }
        // 是否压缩
        if (w < width || h < height) {
            // 不压缩-是否加水印
            if (needWatermark) {
                // 不压缩，加水印
                addImgWatermark(srcImagePath, destImagePath, 100);
            }
            return;
        }
        // 压缩-是否加水印
        if (needWatermark) {
            // 压缩-加水印比例
            double cropRatio = 0f;
            if ((width + 0.0) / (w + 0.0) > (height + 0.0) / (h + 0.0)) {
                cropRatio = (height + 0.0) / (h + 0.0);
            } else {
                cropRatio = (width + 0.0) / (w + 0.0);
            }
            IMOperation op = new IMOperation();
            ImageCommand cmd = getImageCommand(CommandType.compositecmd);
            op.geometry(watermarkImage.getWidth(null), watermarkImage.getHeight(null),
                    (int) (w * cropRatio) - watermarkImage.getWidth(null) - 10,
                    (int) (h * cropRatio) - watermarkImage.getHeight(null) - 10);
            op.addImage(watermarkImagePath);
            op.addImage(srcImagePath);
            op.quality(quality);
            op.resize(width, height);
            op.addImage(createDirectory(destImagePath));
            cmd.run(op);
            return;
        }

        // 压缩-不加水印
        ImageCommand cmd = getImageCommand(CommandType.convert);
        IMOperation op = new IMOperation();
        op.addImage(srcImagePath);
        op.quality(quality);
        op.resize(width, height);
        op.addImage(createDirectory(destImagePath));
        cmd.run(op);
    }

    /**
     * 去除Exif信息，可减小文件大小
     *
     * @param srcImagePath 源图片路径
     * @param destImagePath 目标图片路径
     * @throws Exception
     */
    public static void removeProfile(String srcImagePath, String destImagePath) throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(srcImagePath);
        op.profile("*");
        op.addImage(createDirectory(destImagePath));
        ImageCommand cmd = getImageCommand(CommandType.convert);
        cmd.run(op);
    }

    /**
     * 等比缩放图片（如果width为空，则按height缩放; 如果height为空，则按width缩放）
     *
     * @param srcImagePath 源图片路径
     * @param destImagePath 目标图片路径
     * @param width 缩放后的宽度
     * @param height 缩放后的高度
     * @throws Exception
     */
    public static void scaleResize(String srcImagePath, String destImagePath, Integer width,
                                   Integer height) throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(srcImagePath);
        op.sample(width, height);
        op.addImage(createDirectory(destImagePath));
        ImageCommand cmd = getImageCommand(CommandType.convert);
        cmd.run(op);
    }

    /**
     * 从原图中裁剪出新图
     *
     * @param srcImagePath 源图片路径
     * @param destImagePath 目标图片路径
     * @param x 原图左上角
     * @param y 原图左上角
     * @param width 新图片宽度
     * @param height 新图片高度
     * @throws Exception
     */
    public static void crop(String srcImagePath, String destImagePath, int x, int y, int width,
                            int height) throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(srcImagePath);
        op.crop(width, height, x, y);
        op.addImage(createDirectory(destImagePath));
        ImageCommand cmd = getImageCommand(CommandType.convert);
        cmd.run(op);
    }

    /**
     * 将图片分割为若干小图
     *
     * @param srcImagePath 源图片路径
     * @param destImagePath 目标图片路径
     * @param width 指定宽度（默认为完整宽度）
     * @param height 指定高度（默认为完整高度）
     * @return 小图路径
     * @throws Exception
     */
    public static java.util.List<String> subsection(String srcImagePath, String destImagePath, Integer width,
                                                    Integer height) throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(srcImagePath);
        op.crop(width, height);
        op.addImage(createDirectory(destImagePath));

        ImageCommand cmd = getImageCommand(CommandType.convert);
        cmd.run(op);

        return getSubImages(destImagePath);
    }

    /**
     * 旋转图片
     *
     * @param srcImagePath 源图片路径
     * @param destImagePath 目标图片路径
     * @param angle 旋转的角度
     * @return
     * @throws Exception
     */
    public static void rotate(String srcImagePath, String destImagePath, Double angle)
            throws Exception {
        File sourceFile = new File(srcImagePath);
        if (!sourceFile.exists() || !sourceFile.canRead() || !sourceFile.isFile()) {
            return;
        }

        BufferedImage buffimg = ImageIO.read(sourceFile);
        int w = buffimg.getWidth();
        int h = buffimg.getHeight();
        // 目标图片
        // if (w > h) { //如果宽度不大于高度则旋转过后图片会变大
        ImageCommand cmd = getImageCommand(CommandType.convert);
        IMOperation operation = new IMOperation();
        operation.addImage(srcImagePath);
        operation.rotate(angle);
        operation.addImage(destImagePath);
        cmd.run(operation);
        // }
    }

    /**
     * 获取图片分割后的小图路径
     *
     * @param destImagePath 目标图片路径
     * @return 小图路径
     */
    private static java.util.List<String> getSubImages(String destImagePath) {
        // 文件所在目录
        String fileDir = destImagePath.substring(0, destImagePath.lastIndexOf(File.separatorChar));
        // 文件名称
        String fileName = destImagePath
                .substring(destImagePath.lastIndexOf(File.separatorChar) + 1);
        // 文件名（无后缀）
        String n1 = fileName.substring(0, fileName.lastIndexOf("."));
        // 后缀
        String n2 = fileName.replace(n1, "");

        java.util.List<String> fileList = new ArrayList<String>();
        String path = null;
        for (int i = 0;; i++) {
            path = fileDir + File.separatorChar + n1 + "-" + i + n2;
            if (new File(path).exists())
                fileList.add(path);
            else
                break;
        }
        return fileList;
    }

    /**
     * 创建目录
     *
     * @param path
     * @return path
     */
    private static String createDirectory(String path) {
        File file = new File(path);
        if (!file.exists())
            file.getParentFile().mkdirs();
        return path;
    }

    /**
     * 通过文件名获取文件类型
     *
     * @param fileName 文件名
     */
    private static String getFileType(String fileName) {
        if (fileName == null || "".equals(fileName.trim()) || fileName.lastIndexOf(".") == -1) {
            return null;
        }
        String type = fileName.substring(fileName.lastIndexOf(".") + 1);
        return type.toLowerCase();
    }



    public static void drawTextInImg(String filePath,String outPath, String content) {
        ImageIcon imgIcon = new ImageIcon(filePath);
        Image img = imgIcon.getImage();
        int width = img.getWidth(null);
        int height = img.getHeight(null);



        int fontSize;
        int distence;
        if (width >= height) {
            fontSize = width / 45;
            distence = width / 60;
        } else {
            fontSize = height / 45;
            distence = height / 60;
        }


        BufferedImage bimage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = bimage.createGraphics();
        g.setColor(getColor("#C0C0C0"));
        g.setBackground(Color.white);
        BasicStroke basicStroke = new BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 15.0f, new float[]{10.0f, 10.0f}, 5.0f);

        g.setStroke(basicStroke);
        g.drawImage(img, 0, 0, null);
        //黑体
        Font font = new Font("Ubuntu Mono", Font.BOLD, fontSize);
//        if (StringUtil.isNotEmpty(text.getWm_text_font())
//                && text.getWm_text_size() != null) {
//            font = new Font(text.getWm_text_font(), Font.BOLD, fontSize);
//        } else {
//            font = new Font(null, Font.BOLD, 15);
//        }

        g.setFont(font);
        FontMetrics metrics = new FontMetrics(font){};
        Rectangle2D bounds = metrics.getStringBounds(content, null);
        int textWidth = (int) bounds.getWidth();
        int textHeight = (int) bounds.getHeight();
        int left = 0;
        int top = textHeight;

//todo
        left = width - textWidth - distence;
        top = height - textHeight - distence;

        //九宫格控制位置
//        if(text.getWm_text_pos()==2){
//            left = width/2;
//        }
//        if(text.getWm_text_pos()==3){
//            left = width -textWidth;
//        }
//        if(text.getWm_text_pos()==4){
//            top = height/2;
//        }
//        if(text.getWm_text_pos()==5){
//            left = width/2;
//            top = height/2;
//        }
//        if(text.getWm_text_pos()==6){
//            left = width -textWidth;
//            top = height/2;
//        }
//        if(text.getWm_text_pos()==7){
//            top = height - textHeight;
//        }
//        if(text.getWm_text_pos()==8){
//            left = width/2;
//            top = height - textHeight;
//        }
//        if(text.getWm_text_pos()==9){
//            left = width -textWidth;
//            top = height - textHeight;
//        }
        g.drawString(content, left, top);
        g.dispose();

        try {
            FileOutputStream out = new FileOutputStream(outPath);
            ImageIO.write(bimage, "JPEG", out);
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // color #2395439
    public static Color getColor(String color) {
        if (color.charAt(0) == '#') {
            color = color.substring(1);
        }
        if (color.length() != 6) {
            return null;
        }
        try {
            int r = Integer.parseInt(color.substring(0, 2), 16);
            int g = Integer.parseInt(color.substring(2, 4), 16);
            int b = Integer.parseInt(color.substring(4), 16);
            return new Color(r, g, b);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }



    public static class FontText {
        private String text;

        private int wm_text_pos;

        private String wm_text_color;

        private Integer wm_text_size;

        private String wm_text_font;//字体  “黑体，Arial”

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getWm_text_pos() {
            return wm_text_pos;
        }

        public void setWm_text_pos(int wm_text_pos) {
            this.wm_text_pos = wm_text_pos;
        }

        public String getWm_text_color() {
            return wm_text_color;
        }

        public void setWm_text_color(String wm_text_color) {
            this.wm_text_color = wm_text_color;
        }

        public Integer getWm_text_size() {
            return wm_text_size;
        }

        public void setWm_text_size(Integer wm_text_size) {
            this.wm_text_size = wm_text_size;
        }

        public String getWm_text_font() {
            return wm_text_font;
        }

        public void setWm_text_font(String wm_text_font) {
            this.wm_text_font = wm_text_font;
        }

        public FontText(String text, int wm_text_pos, String wm_text_color,
                        Integer wm_text_size, String wm_text_font) {
            super();
            this.text = text;
            this.wm_text_pos = wm_text_pos;
            this.wm_text_color = wm_text_color;
            this.wm_text_size = wm_text_size;
            this.wm_text_font = wm_text_font;
        }

        public FontText(){}
    }


    public static void main(String[] args) {
//        String filePath = "/var/upload/bbs/21723065.jpg";
//        String outPath = "/var/upload/bbs/me.jpg";
//        drawTextInImg(filePath, outPath,"photo by chenxb");
//
//
        GraphicsEnvironment eq = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontsName = eq.getAvailableFontFamilyNames();
        for (String s : fontsName) {

            System.out.println(s);
        }
        String path = "/var/upload/bbs/21723065.jpg";
        String toPath = "/var/upload/bbs/ceshi.jpg";
        String a = "WO我是陈旭彪b";

        try {
            addTextWatermark(path, toPath, a, 100, 36);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setFontString(String fontString) {
        if (StringUtil.isNotBlank(fontString)) {
            FONT_STRING = fontString.trim();
        }
    }
}
