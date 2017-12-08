package com.zhgtrade.chat.service;


import com.zhgtrade.model.Privatechat;

import java.util.List;
import java.util.Map;


/**
 * @author xxp
 * @version 2017- 09- 27 15:26
 * @description
 * @copyright www.zhgtrade.com
 */
public interface PrivatechatService {

    Privatechat insert(Privatechat privatechatMsg);

    List<Map> getPrivatechat(int userid, int start, int limit);

    void update(Privatechat privatechat);
    int findByParamCount(int userId);
    int findChatMsgCount(int userId);
    Privatechat getChatByParam(int sendUserid, int receiveUserId);

}
