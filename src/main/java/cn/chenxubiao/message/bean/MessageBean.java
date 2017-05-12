package cn.chenxubiao.message.bean;

import cn.chenxubiao.message.domain.Message;

import java.util.List;

/**
 * Created by chenxb on 17-5-11.
 */
public class MessageBean {
    private int msgCount;
    private List<Message> messages;

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
