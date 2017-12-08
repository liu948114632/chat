package com.zhgtrade.bean;

/**
 * @author xxp
 * @version 2017- 09- 27 17:10
 * @description
 * @copyright www.zhgtrade.com
 */
public class MsgBean {
    private Integer type;
    private int sendUserId;
    private int receiveUserId;
    private String msgContent;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public int getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(int sendUserId) {
        this.sendUserId = sendUserId;
    }

    public int getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(int receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}
