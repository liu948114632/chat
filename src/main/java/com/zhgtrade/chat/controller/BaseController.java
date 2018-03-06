package com.zhgtrade.chat.controller;

import com.zhgtrade.dto.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : xuelin
 * Date： 2016/11/1
 */
public class BaseController {
    @Autowired(required = false)
    private HttpServletRequest request;
    @Autowired(required = false)
    private HttpServletResponse response;

    protected final static int SUCCESS = 0;                 // 处理成功
    protected final static int UNKNOWN_ERROR = -1;           // 未知错误

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public HttpSession getSession() {
        return this.request.getSession(true);
    }




    protected ResultBean forSuccessResult(Object data){
        return forSuccessResult(data, 0);
    }

    protected ResultBean forSuccessResult(){
        return forSuccessResult(null, 0);
    }

    protected ResultBean forSuccessResult(Object data, int totalCount){
        return new ResultBean(SUCCESS, data, totalCount);
    }

    protected ResultBean forSuccessResult(Object data, int totalCount, int page){
        return new ResultBean(SUCCESS, data, totalCount, page);
    }
    protected ResultBean forSuccessResult(Object data, int totalCount, int page, int id){
        return new ResultBean(SUCCESS, data, totalCount, page,id);
    }

    protected ResultBean forFailureResult(int code, Object data, String message){
        return new ResultBean(code, data, message);
    }

    protected ResultBean forFailureResult(int code){
        return forFailureResult(code, null, null);
    }

    protected ResultBean forFailureResult(int code, Object data){
        return forFailureResult(code, data, null);
    }

    /**
     * api异常
     *
     * @return
     */
    @ExceptionHandler(Exception.class)
    protected Object exceptionHandler(Exception e){
        e.printStackTrace();
        return forFailureResult(UNKNOWN_ERROR, null, "系统异常");
    }





}
