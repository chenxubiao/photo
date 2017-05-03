package cn.chenxubiao.project.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.project.service.ProjectInfoService;
import cn.chenxubiao.project.service.ProjectLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by chenxb on 17-4-29.
 */
@RestController
public class ProjectLikeController extends CommonController {

    @Autowired
    private ProjectLikeService projectLikeService;
    @Autowired
    private ProjectInfoService projectInfoService;

    @RequestMapping(value = "/project/like/update", method = RequestMethod.POST)
    public ResponseEntity like(@RequestParam(value = "picId", defaultValue = "0") int picId,
                               HttpServletRequest request) {

        if (picId <= 0) {
            return ResponseEntity.failure(Errors.PARAMETER_ILLEGAL);
        }
        UserSession userSession = getUserSession(request);
        if (userSession == null) {
            return ResponseEntity.failure(Errors.LOGIN_FIRST, 1);
        }
        int userId = userSession.getUserId();
        ProjectInfo projectInfo = projectInfoService.findByPicId(picId);
        if (projectInfo == null) {
            return ResponseEntity.failure(Errors.PROJECT_NOT_FOUND);
        }
        int projectId = projectInfo.getId();
        projectLikeService.disposeLike(userId, projectId);
        return ResponseEntity.success();
    }
}
