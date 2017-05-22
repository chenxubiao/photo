package cn.chenxubiao.picture.web;

import cn.chenxubiao.account.domain.Account;
import cn.chenxubiao.account.domain.AccountLog;
import cn.chenxubiao.account.enums.AccountLogTypeEnum;
import cn.chenxubiao.account.service.AccountLogService;
import cn.chenxubiao.account.service.AccountService;
import cn.chenxubiao.common.bean.UserSession;
import cn.chenxubiao.common.utils.ConstStrings;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.common.utils.consts.Errors;
import cn.chenxubiao.common.web.CommonController;
import cn.chenxubiao.message.domain.Message;
import cn.chenxubiao.message.enums.MessageTypeEnum;
import cn.chenxubiao.message.service.MessageService;
import cn.chenxubiao.picture.domain.Attachment;
import cn.chenxubiao.picture.domain.DownloadLog;
import cn.chenxubiao.picture.service.AttachmentService;
import cn.chenxubiao.picture.service.DownloadLogService;
import cn.chenxubiao.picture.utils.DownloadUtil;
import cn.chenxubiao.project.domain.ProjectInfo;
import cn.chenxubiao.project.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxb on 17-4-28.
 */
@Controller
public class PictureDownloadController extends CommonController {
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountLogService accountLogService;
    @Autowired
    private ProjectInfoService projectInfoService;
    @Autowired
    private DownloadLogService downloadLogService;
    @Autowired
    private MessageService messageService;


    @RequestMapping(value = "/picture/download", method = RequestMethod.GET)

    public String downloadPicture(@RequestParam(value = "id", defaultValue = "0") int id,
                                  HttpServletResponse response, HttpServletRequest request,
                                  Map<String, Object> map) {

        if (id <= 0) {
            map.put("message", "id不合法");
            return "error";
        }
        Attachment attachment = attachmentService.findById(id);
        if (attachment == null) {
            map.put("message", Errors.PICTURE_NOT_FOUND);
            return "error";
        }

        UserSession userSession = super.getUserSession(request);
        ProjectInfo projectInfo = projectInfoService.findByPicId(attachment.getId());
        if (projectInfo == null || projectInfo.getAuth() == BBSConsts.ProjectAuth.NONE_AUTH) {
            map.put("message", Errors.PICTURE_NOT_FOUND);
            return "error";
        }

        if (userSession.getUserId() != attachment.getUserId()) {
            Account accountReduce = accountService.findByUserId(userSession.getUserId());
            AccountLog auth = accountLogService.findByPicAuth
                    (accountReduce.getUserId(), AccountLogTypeEnum.DEL_PIC_DOWNLOAD.getCode(), projectInfo.getId(), accountReduce);
            if (auth == null) {
                if (accountReduce.getTotalMoney() < projectInfo.getMoney()) {
                    map.put("message", Errors.ACCOUNT_BALANCE);
                    return "error";
                }
                List<AccountLog> accountLogList = new ArrayList<>();
                int pay = projectInfo.getMoney();
                AccountLog reduceLog = new AccountLog
                        (userSession.getUserId(), AccountLogTypeEnum.DEL_PIC_DOWNLOAD.getCode(), pay, projectInfo.getId(),
                                projectInfo.getTitle(), accountReduce);
                reduceLog.setBalance(accountReduce.getTotalMoney() - pay);
                reduceLog.setCreateTime(new Date());
                reduceLog.setModifyTime(reduceLog.getCreateTime());
                accountReduce.setTotalMoney(accountReduce.getTotalMoney() - pay);
                accountReduce.setModifyTime(new Date());
                accountService.save(accountReduce);

                accountLogList.add(reduceLog);

                Account accountAdd = accountService.findByUserId(attachment.getUserId());
                accountAdd.setTotalMoney(accountAdd.getTotalMoney() + pay);
                accountAdd.setModifyTime(new Date());
                accountService.save(accountAdd);
                AccountLog addLog = new AccountLog
                        (attachment.getUserId(), AccountLogTypeEnum.ADD_PIC_DOWNLOAD.getCode(), pay, projectInfo.getId(),
                                projectInfo.getTitle(), accountAdd);
                addLog.setBalance(accountAdd.getTotalMoney());
                addLog.setCreateTime(new Date());
                addLog.setModifyTime(addLog.getCreateTime());
                accountLogList.add(addLog);
                accountLogService.saveAll(accountLogList);

                Message message = new Message
                        (MessageTypeEnum.ACCOUNT_CHANGE.getCode(), 1, accountAdd.getUserId(), addLog.getId(), addLog.getMessage());
                message.setCreateTime(new Date());
                message.setModifyTime(message.getCreateTime());
                messageService.save(message);


                Message msg = new Message
                        (MessageTypeEnum.ACCOUNT_CHANGE.getCode(), 1, reduceLog.getUserId(), reduceLog.getId(), reduceLog.getMessage());
                msg.setCreateTime(new Date());
                msg.setModifyTime(message.getCreateTime());
                messageService.save(msg);
            }
        }

        DownloadLog downloadLog = new DownloadLog();
        downloadLog.setPicId(attachment.getId());
        downloadLog.setOwnerId(attachment.getUserId());
        downloadLog.setDownloaderId(userSession.getUserId());
        downloadLog.setCreateTime(new Date());
        downloadLog.setModifyTime(downloadLog.getCreateTime());
        downloadLogService.save(downloadLog);

        String relativePath = BBSConsts.PROTECTED_BASE_PATH + attachment.getRelativePath();
        response.setContentType(ConstStrings.CONTENT_TYPE_DOWNLOAD);//图片下载
        DownloadUtil.downloadPicture(response, relativePath);
        return null;
    }
}
