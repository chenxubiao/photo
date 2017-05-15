package cn.chenxubiao.picture.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by chenxb on 17-5-14.
 */
@Entity
@Table(name = "bbs_picture_download_log")
public class DownloadLog implements Serializable {

    private static final long serialVersionUID = 8672325132117293433L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int picId;
    private int ownerId;
    private int downloaderId;
    @Column(name = "createTime", updatable = false)
    private Date createTime;
    @Column(name = "modifyTime")
    private Date modifyTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getDownloaderId() {
        return downloaderId;
    }

    public void setDownloaderId(int downloaderId) {
        this.downloaderId = downloaderId;
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
}
