package cn.chenxubiao.project.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by chenxb on 17-5-9.
 */
@Entity
@Table(name = "bbs_project_view")
public class ProjectView implements Serializable {

    private static final long serialVersionUID = 7764367292747716542L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int userId;     //项目拥有者id
    private int projectId;  //项目id
    private int viewer;     //查看者id
    @Column(name = "createTime", updatable = false)
    private Date createTime;
    @Column(name = "modifyTime")
    private Date modifyTime;

    public ProjectView() {
    }

    public ProjectView(ProjectInfo projectInfo, int viewer) {
        this.projectId = projectInfo.getId();
        this.userId = projectInfo.getUserId();
        this.viewer = viewer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getViewer() {
        return viewer;
    }

    public void setViewer(int viewer) {
        this.viewer = viewer;
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
