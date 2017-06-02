package cn.chenxubiao.picture.service;

import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.picture.domain.PictureExif;
import cn.chenxubiao.picture.repository.PictureExifRepository;
import cn.chenxubiao.project.service.ProjectLikeService;
import cn.chenxubiao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by chenxb on 17-4-1.
 */
@Service
public class PictureExifServiceImpl implements PictureExifService {
    @Autowired
    private PictureExifRepository pictureExifRepository;

    @Override
    public void save(PictureExif pictureExif) {
        if (pictureExif == null) {
            return;
        }
        int count = countByAttachmentId(pictureExif.getPicId());
        if (count > 0) {
            return;
        }
        pictureExifRepository.save(pictureExif);
    }

    @Override
    public int countByAttachmentId(int id) {
        if (id <= 0) {
            return 0;
        }
        return pictureExifRepository.countByPicId(id);
    }

    @Override
    public Page<PictureExif> findByPage(Pageable pageable) {
        return pictureExifRepository.findByPage(pageable);
    }

    @Override
    public List<PictureExif> findInPicIds(Set<Integer> picIds) {
        if (CollectionUtil.isEmpty(picIds)) {
            return null;
        }
        return pictureExifRepository.findInPicIds(picIds);
    }

    @Override
    public PictureExif findByPicId(int picId) {
        if (picId <= 0) {
            return null;
        }
        return pictureExifRepository.findByPicId(picId);
    }


}
