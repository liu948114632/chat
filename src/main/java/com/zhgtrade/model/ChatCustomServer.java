package com.zhgtrade.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chat_custom_service")
public class ChatCustomServer {

    @Id
    @GeneratedValue
    private  Integer id;
    @Column(name = "userid", unique = true, nullable = false)
    private Integer userid;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "work_time")
    private Integer workTime;
    private Integer online;//1 在线 2离线',

    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getWorkTime() {
        return workTime;
    }
    public void setWorkTime(Integer workTime) {
        this.workTime = workTime;
    }
    public Integer getOnline() {
        return online;
    }
    public void setOnline(Integer online) {
        this.online = online;
    }
}