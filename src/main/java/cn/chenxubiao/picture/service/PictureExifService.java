package cn.chenxubiao.picture.service;

import cn.chenxubiao.picture.domain.PictureExif;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * Created by chenxb on 17-4-1.
 */
public interface PictureExifService {
    void save(PictureExif pictureExif);

    int countByAttachmentId(int id);

    Page<PictureExif> findByPage(Pageable pageable);

    List<PictureExif> findInPicIds(Set<Integer> picIds);

    PictureExif findByPicId(int picId);

}
