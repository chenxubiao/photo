package cn.chenxubiao.tag.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by chenxb on 17-4-1.
 */
@Entity
@Table(name = "bbs_tag_relation")
public class TagRelation implements Serializable {
    private static final long serialVersionUID = 7716613964415877043L;
    private int startTagId;
    private int endTagId;
    private int status;
    private int score;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "createTime", updatable = false)
    private Date createTime;
    @Column(name = "modifyTime")
    private Date modifyTime;

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

    public int getStartTagId() {
        return startTagId;
    }

    public void setStartTagId(int startTagId) {
        this.startTagId = startTagId;
    }

    public int getEndTagId() {
        return endTagId;
    }

    public void setEndTagId(int endTagId) {
        this.endTagId = endTagId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
