package cn.chenxubiao.project.service;

import cn.chenxubiao.picture.bean.PicInfoBean;
import cn.chenxubiao.project.domain.ProjectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by chenxb on 17-4-1.
 */
public interface ProjectInfoService {
    ProjectInfo findByPicId(int picId);

    List<PicInfoBean> findPicInfoByPage(Pageable pageable);

    List<PicInfoBean> findPicInfoByPage(Pageable pageable, int userId);

    Page<ProjectInfo> findByPage(Pageable pageable);

    void save(ProjectInfo projectInfo);
}
