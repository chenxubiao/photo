package cn.chenxubiao.message.service;

import cn.chenxubiao.common.utils.CollectionUtil;
import cn.chenxubiao.common.utils.consts.BBSConsts;
import cn.chenxubiao.message.bean.SenderInfo;
import cn.chenxubiao.message.domain.Message;
import cn.chenxubiao.message.repository.MessageRepository;
import cn.chenxubiao.user.domain.UserInfo;
import cn.chenxubiao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenxb on 17-5-11.
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public void save(Message message) {
        if (message == null) {
            return;
        }
        messageRepository.save(message);
    }

    @Override
    public int countUnLookMsg(int userId) {
        if (userId <= 0) {
            return 0;
        }
        return messageRepository.countByReceiverAndStatus(userId, BBSConsts.MessageStatus.SEND);
    }

    @Override
    public List<Message> findUnLookMsg(int userId) {
        if (userId <= 0) {
            return null;
        }
        List<Message> messageList = messageRepository.findAllByReceiverAndStatusOrderByIdDesc(userId, BBSConsts.MessageStatus.SEND);
        if (CollectionUtil.isNotEmpty(messageList)) {
            for (Message message : messageList) {
                if (message.getSender() > 0) {
                    UserInfo userInfo = userInfoService.findById(message.getSender());
                    if (userInfo != null) {
                        SenderInfo senderInfo = new SenderInfo(userInfo);
                        message.setSenderInfo(senderInfo);
                    }
                } else {
                    if (message.getType() == BBSConsts.MessageType.ACCOUNT_CHANGE) {
                        message.setMessage("账户变动通知：" + message.getMessage() + "朵小红花,欢迎使用～");
                    }
                    SenderInfo senderInfo = new SenderInfo();
                    senderInfo.setAvatarId(0);
                    senderInfo.setUserId(0);
                    senderInfo.setUserName(BBSConsts.BBS_NAME);
                    message.setSenderInfo(senderInfo);
                }
            }
        }
        return messageList;
    }

    @Override
    public void updateUnLookMsg(int userId) {
        if (userId <= 0) {
            return;
        }
        List<Message> messageSendList = messageRepository.findAllByReceiverAndStatusOrderByIdDesc(userId, BBSConsts.MessageStatus.SEND);
        if (CollectionUtil.isNotEmpty(messageSendList)) {
            List<Message> messageViewList = new ArrayList<>();
            for (Message message : messageSendList) {
                message.setStatus(BBSConsts.MessageStatus.VIEWD);
                message.setModifyTime(new Date());
                messageViewList.add(message);
            }
            messageRepository.save(messageViewList);
        }
    }

}
