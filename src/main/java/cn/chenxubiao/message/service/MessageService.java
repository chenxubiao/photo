package cn.chenxubiao.message.service;

import cn.chenxubiao.message.domain.Message;

import java.util.List;

/**
 * Created by chenxb on 17-5-11.
 */
public interface MessageService {
    void save(Message message);

    int countUnLookMsg(int userId);

    List<Message> findUnLookMsg(int userId);

    void updateUnLookMsg(int userId);
}
