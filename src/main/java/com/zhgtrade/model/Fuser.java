package com.zhgtrade.model;

import java.io.Serializable;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/12/8
 */
public class Fuser implements Serializable{
    int fid;
    String fnickName;
    String femail;
    String ftelephone;
    boolean rank;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFnickName() {
        return fnickName;
    }

    public void setFnickName(String fnickName) {
        this.fnickName = fnickName;
    }

    public String getFemail() {
        return femail;
    }

    public void setFemail(String femail) {
        this.femail = femail;
    }

    public String getFtelephone() {
        return ftelephone;
    }

    public void setFtelephone(String ftelephone) {
        this.ftelephone = ftelephone;
    }

    public boolean isRank() {
        return rank;
    }

    public void setRank(boolean rank) {
        this.rank = rank;
    }
}
