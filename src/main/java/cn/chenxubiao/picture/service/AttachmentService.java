package cn.chenxubiao.picture.service;

import cn.chenxubiao.picture.domain.Attachment;

/**
 * Created by chenxb on 17-4-1.
 */
public interface PictureAttachmentService {
    Attachment save(Attachment attachment);

    Attachment findById(int id);

    boolean isPictureExist(int id);
}
