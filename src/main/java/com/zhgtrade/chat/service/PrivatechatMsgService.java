package com.zhgtrade.chat.service;


import com.zhgtrade.model.PrivatechatMsg;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author xxp
 * @version 2017- 09- 27 15:26
 * @description
 * @copyright www.zhgtrade.com
 */
public interface PrivatechatMsgService {

    PrivatechatMsg insertMsg(PrivatechatMsg privatechatMsg);

    List<Map> getPrivatechatMsg(int userid, int touserid, int start, int limit);

    int updateMsg(PrivatechatMsg privatechatMsg);
    int findByParamCount(int userId, int toUserId);

}
