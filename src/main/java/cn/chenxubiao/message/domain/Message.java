package cn.chenxubiao.message.domain;

import cn.chenxubiao.common.utils.DateStringFormatUtil;
import cn.chenxubiao.message.bean.SenderInfo;
import cn.chenxubiao.message.enums.MessageStatusEnum;
import cn.chenxubiao.message.enums.MessageTypeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by chenxb on 17-5-11.
 */
@Entity
@Table(name = "bbs_message")
public class Message implements Serializable {

    private static final long serialVersionUID = -6726439336174002333L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int type;
    private int projectId;  //相关id
    private int sender;     //发送者
    private int receiver;   //接收者
    private String message; //信息
    private int status;     //状态
    @Column(name = "createTime", updatable = false)
    private Date createTime;
    @Column(name = "modifyTime")
    private Date modifyTime;
    @Transient
    private String time;
    @Transient
    private SenderInfo senderInfo;
    @Transient
    private SenderInfo receiverInfo;

    public Message() {

    }

    public Message(int type, int sender, int receiver, int projectId, String message) {
        this.sender = sender;
        this.status = MessageStatusEnum.SEND.getCode();
        this.type = type;
        this.receiver = receiver;
        this.projectId = projectId;
        this.message = message;
        this.createTime = new Date();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        if (this.type == MessageTypeEnum.PROJECT_LIKE.getCode()) {
            this.message = MessageTypeEnum.PROJECT_LIKE.getValue() + this.message;
        } else if (this.type == MessageTypeEnum.PROJECT_UNLIKE.getCode()) {
            this.message = MessageTypeEnum.PROJECT_UNLIKE.getValue() + this.message;
        } else if (this.type == MessageTypeEnum.USER_FOLLOW.getCode()) {
            this.message = MessageTypeEnum.USER_FOLLOW.getValue();
        } else if (this.type == MessageTypeEnum.USER_UNFOLLOW.getCode()) {
            this.message = MessageTypeEnum.USER_UNFOLLOW.getValue();
        }else if (this.type == MessageTypeEnum.REGEISTER.getCode()) {
            this.message = "hi～" + this.message + ",欢迎来到图片社区，请吃饭请联系：hfutchenxb@163.com";
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public SenderInfo getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(SenderInfo senderInfo) {
        this.senderInfo = senderInfo;
    }

    public String getTime() {
        return DateStringFormatUtil.format(this.createTime);
    }

    public SenderInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(SenderInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }
}
