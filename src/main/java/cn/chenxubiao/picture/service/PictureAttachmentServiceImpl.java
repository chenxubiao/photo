package cn.chenxubiao.picture.service;

import cn.chenxubiao.picture.domain.PictureAttachment;
import cn.chenxubiao.picture.repository.PictureAttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenxb on 17-4-1.
 */
@Service
public class PictureAttachmentServiceImpl implements PictureAttachmentService {
    @Autowired
    private PictureAttachmentRepository attachmentRepository;

    @Override
    public PictureAttachment save(PictureAttachment attachment) {
        if (attachment == null) {
            return null;
        }
        return attachmentRepository.save(attachment);
    }

    @Override
    public PictureAttachment findById(int id) {
        if (id <= 0) {
            return null;
        }
        return attachmentRepository.findById(id);
    }

    @Override
    public boolean isPictureExist(int id) {
        if (id <= 0) {
            return false;
        }
        return attachmentRepository.countById(id) > 0;
    }

}
