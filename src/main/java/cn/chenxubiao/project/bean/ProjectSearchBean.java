package cn.chenxubiao.project.bean;

import cn.chenxubiao.picture.domain.PictureExif;
import cn.chenxubiao.user.bean.UserInfoBean;

/**
 * Created by chenxb on 17-5-12.
 */
public class ProjectSearchBean {
    private int projectId;
    private int picId;
    private String title;
    private PictureExif exif;
    private int width;
    private int height;
    private int liked;
    private int likeNum;
    private int views;
    private UserInfoBean user;
}
