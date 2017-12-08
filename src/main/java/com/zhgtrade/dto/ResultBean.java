package com.zhgtrade.dto;

/**
 * Author:xuelin
 * Date:2017/9/6
 * Desc:
 */
public class ResultBean {

    // 未登录
    public final static ResultBean E401 = new ResultBean(401);
    // 未实名
    public final static ResultBean E402 = new ResultBean(402);
    // 强制下线
    public final static ResultBean E403 = new ResultBean(403);

    private int code;
    private Object data;
    private int totalCount;
    private int page;
    private String msg;
    private Integer id;

    public ResultBean() {
    }

    public ResultBean(int code) {
        this.code = code;
    }

    public ResultBean(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public ResultBean(int code, Object data, int totalCount) {
        this.code = code;
        this.data = data;
        this.totalCount = totalCount;
    }

    public ResultBean(int code, Object data, int totalCount, int page) {
        this.code = code;
        this.data = data;
        this.totalCount = totalCount;
        this.page = page;
    }

    public ResultBean(int code, Object data, int totalCount, int page, int id) {
        this.code = code;
        this.data = data;
        this.totalCount = totalCount;
        this.page = page;
        this.id = id;
    }

    public ResultBean(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
