package cn.chenxubiao.project.service;

import cn.chenxubiao.picture.bean.PicInfoBean;
import cn.chenxubiao.project.bean.ProjectBean;
import cn.chenxubiao.project.bean.ProjectInfoBean;
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

    ProjectInfo save(ProjectInfo projectInfo);

    ProjectInfo findProjectInDB(ProjectInfoBean projectInfoBean);

    List<ProjectInfo> findByUserAndPage(int userId, Pageable pageable);


    ProjectInfo findFormerProject(ProjectInfo projectInfo);

    ProjectInfo findLatterProject(ProjectInfo projectInfo);

    List<ProjectInfo> findByUserId(int userId);

    ProjectInfo findById(int id);

    Page<ProjectInfo> findInUserIdAndPage(List<Integer> userIds, Pageable pageable);

    List<ProjectInfo> search(String name, int sessonUserId);

    int countProjectNum(int userId);

    Page<ProjectInfo> findLatest(Pageable pageable);

    List<ProjectInfo> searchByTag(String name);
}
