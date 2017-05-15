package cn.chenxubiao.account.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by chenxb on 17-5-13.
 */


/**
 * one端
 *
 * 碰到many为末端的加载就是延迟加载，若one为末端则为立即加载，除了one-to-one。
 *
 * @author jiqinlin
 *
 */
@Entity
@Table(name = "bbs_account")
public class Account implements Serializable {

    private static final long serialVersionUID = 1711161088647112723L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "createTime", updatable = false)
    private Date createTime;
    @Column(name = "modifyTime")
    private Date modifyTime;
    private int userId;
    private int totalMoney;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
    Set<AccountLog> logs = new LinkedHashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Set<AccountLog> getLogs() {
        return logs;
    }

    public void setLogs(Set<AccountLog> logs) {
        this.logs = logs;
    }

    /**
     * 添加订单项
     *
     * @param student
     */
    public void addAccountLog(AccountLog log) {
        if (!this.logs.contains(log)) {
            this.logs.add(log);
            log.setAccount(this);
        }
    }

    /**
     * 删除订单项
     *
     * @param student
     */
    public void removeAccountLog(AccountLog log) {
        if (this.logs.contains(log)) {
            log.setAccount(null);
            this.logs.remove(log);
        }
    }
}
