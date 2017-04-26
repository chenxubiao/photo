package cn.chenxubiao.user.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by chenxb on 17-4-1.
 */
@Entity
@Table(name = "bbs_user_follow")
public class UserFollow implements Serializable {
    private static final long serialVersionUID = -3424029786497131328L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "createTime", updatable = false)
    private Date createTime;
    @Column(name = "modifyTime")
    private Date modifyTime;
    private int startUserId;    //被关注者id
    private int endUserId;      //关注者id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(int startUserId) {
        this.startUserId = startUserId;
    }

    public int getEndUserId() {
        return endUserId;
    }

    public void setEndUserId(int endUserId) {
        this.endUserId = endUserId;
    }
}
