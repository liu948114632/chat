package com.zhgtrade.chat.controller;


import com.zhgtrade.chat.service.ChatUserService;
import com.zhgtrade.chat.service.PrivatechatMsgService;
import com.zhgtrade.chat.service.PrivatechatService;
import com.zhgtrade.dao.ChatCustomServerDao;
import com.zhgtrade.model.Fuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/11/14
 */
@RequestMapping("/dope")
@RestController
public class UserChatController{
    @Autowired
    private PrivatechatMsgService privatechatMsgService;
    @Autowired
    private PrivatechatService privatechatService;
    @Autowired
    private ChatUserService userService;
    @Autowired
    private ChatCustomServerDao chatCustomServerDao;

    /**
     *
     * @param userId 自己的id
     * @param toUserId 對方的id
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getChatMsgList")
    public Object getChatMsgList(int userId,int toUserId,
                                      @RequestParam(defaultValue = "0", required = false) int start,
                                      @RequestParam(required = false, defaultValue = "15") int limit) {
        Fuser user = this.userService.getUserInfo(userId);
        Fuser user2 = this.userService.getUserInfo(toUserId);
        int count = privatechatMsgService.findByParamCount(userId,toUserId);
        List<Map> list = privatechatMsgService.getPrivatechatMsg(userId,toUserId,start,limit + 1);
        if (!CollectionUtils.isEmpty(list)){
            Iterator<Map> it = list.iterator();
            Map map = null;
            while (it.hasNext()){
                map = it.next();
                map.put("nickName",user.getFnickName());
                map.put("nickName2",user2.getFnickName());
            }
        }

        Map result = new HashMap();
        result.put("data",list);
        result.put("count",count);
        result.put("start",start);
        return result;
    }


    /**
     *
     * @param userId 自己的id
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getChatList")
    public Object getChatList(int userId,
                              @RequestParam(defaultValue = "0", required = false) int start,
                              @RequestParam(required = false, defaultValue = "15") int limit) {
        int count = privatechatService.findByParamCount(userId);
        List<Map> list = privatechatService.getPrivatechat(userId,start,limit + 1);
        Iterator<Map> iterator = list.iterator();
        Map map = null;
        Fuser fuser = null;
        while (iterator.hasNext()){
            map = iterator.next();
            fuser = userService.getUserInfo((Integer)map.get("sendUserId"));
            if (fuser == null) continue;
            map.put("nickName2",fuser.getFnickName());
            map.put("email",fuser.getFemail());
            map.put("phone",fuser.getFtelephone());
            map.put("rank",fuser.isRank());
        }
        Map result = new HashMap();
        result.put("data",list);
        result.put("count",count);
        result.put("start",start);
        return result;
    }

    /**
     *
     * @param userId 自己的id
     * @return
     */
    @RequestMapping(value = "/getChatMsgCount")
    public Object getChatMsgCount(int userId) {
        int count = privatechatService.findChatMsgCount(userId);
        Map result = new HashMap();
        result.put("count",count);
        return result;
    }

    @RequestMapping(value = "/getChatCustomServer")
    public Object getChatCustomServer() {
        List<Map> list = chatCustomServerDao.findByParam("SELECT id,userid as userId,update_time " +
                " as updateTime,work_time as workTime,online from chat_custom_service");
        if (!CollectionUtils.isEmpty(list)){
            Iterator<Map> it = list.iterator();
            Map map = null;
            while (it.hasNext()){
                try {
                    map = it.next();
                    map.put("nickName",this.userService.getUserInfo(Integer.parseInt(map.get("userId").toString())).getFnickName());
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }
        Map result = new HashMap();
        result.put("data",list);
        return result;
    }


}