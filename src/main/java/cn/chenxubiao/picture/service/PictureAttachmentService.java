package cn.chenxubiao.picture.service;

import cn.chenxubiao.picture.domain.PictureAttachment;

/**
 * Created by chenxb on 17-4-1.
 */
public interface PictureAttachmentService {
    PictureAttachment save(PictureAttachment attachment);

    PictureAttachment findById(int id);

    boolean isPictureExist(int id);
}
