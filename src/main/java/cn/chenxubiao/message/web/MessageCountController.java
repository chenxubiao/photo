package cn.chenxubiao.message.web;

import cn.chenxubiao.common.bean.ResponseEntity;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenxb on 17-5-16.
 */
@RestController
public class MessageCountController extends CommonController {
    @Autowired
    private MessageService messageService;

    public ResponseEntity findUnLookMsgCount(HttpServletRequest request) {
        UserSession userSession = super.getUserSession(request);
        int userId = userSession.getUserId();
        int count = messageService.countUnLookMsg(userId);
//        return ResponseEntity.success().set(BBSConsts)
        return null;
    }

}
