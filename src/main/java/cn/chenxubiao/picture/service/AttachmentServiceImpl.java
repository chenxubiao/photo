package cn.chenxubiao.picture.service;

import cn.chenxubiao.picture.domain.Attachment;
import cn.chenxubiao.picture.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenxb on 17-4-1.
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private AttachmentRepository attachmentRepository;

    @Override
    public Attachment save(Attachment attachment) {
        if (attachment == null) {
            return null;
        }
        return attachmentRepository.save(attachment);
    }

    @Override
    public Attachment findById(int id) {
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
