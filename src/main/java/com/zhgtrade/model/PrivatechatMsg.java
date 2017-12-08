package com.zhgtrade.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "privatechat_msg")
public class PrivatechatMsg {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "receive_userid")
    private Integer receiveUserId;
    @Column(name = "send_userid")
    private Integer sendUserId;
    private Integer status;
    @Column(name="create_time")
    private Date createTime;
    private Integer type;
    private String content;
    @Transient
    private String timestr;
    @Transient
    private String nickName;
    @Transient
    private String email;
    @Transient
    private String phone;
    @Transient
    private String nickName2;
    @Transient
    private boolean rank;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Integer receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public Integer getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Integer sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestr() {
        return timestr;
    }

    public void setTimestr(String timestr) {
        this.timestr = timestr;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName2() {
        return nickName2;
    }

    public void setNickName2(String nickName2) {
        this.nickName2 = nickName2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isRank() {
        return rank;
    }

    public void setRank(boolean rank) {
        this.rank = rank;
    }
}