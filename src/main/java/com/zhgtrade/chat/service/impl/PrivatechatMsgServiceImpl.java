package com.zhgtrade.chat.service.impl;


import com.zhgtrade.chat.service.PrivatechatMsgService;
import com.zhgtrade.dao.PrivatechatMsgDao;
import com.zhgtrade.model.PrivatechatMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xxp
 * @version 2017- 09- 27 15:32
 * @description
 * @copyright www.zhgtrade.com
 */
@Service
public class PrivatechatMsgServiceImpl implements PrivatechatMsgService {

    @Autowired
    private PrivatechatMsgDao privatechatMsgDao;

    @Override
    public PrivatechatMsg insertMsg(PrivatechatMsg privatechatMsg) {
        privatechatMsgDao.save(privatechatMsg);
        return privatechatMsg;
    }

    @Override
    public List<Map> getPrivatechatMsg(int userId, int toUserId,int start,int limit) {
        String sql = "SELECT id,type,create_time as createTime,send_userid as sendUserId," +
                " receive_userid as receiveUserId,content from privatechat_msg where " +
                "(send_userid = " +userId+
                " and receive_userid=" +toUserId+
                ") or (send_userid = " +toUserId+
                " and receive_userid=" +userId+
                ")  order by create_time desc" ;
        return privatechatMsgDao.findByParam(start, limit, sql, true);
    }

    @Override
    public int updateMsg(PrivatechatMsg privatechatMsg) {
       this.privatechatMsgDao.update(privatechatMsg);
        return 1;
    }

    @Override
    public int findByParamCount(int userId,int toUserId){
        try {
            String sql = "SELECT count(0) from privatechat_msg where " +
                    "(send_userid = " +userId+
                    " and receive_userid=" +toUserId+
                    ") or (send_userid = " +toUserId+
                    " and receive_userid=" +userId+
                    ") ";
            return privatechatMsgDao.findByParamCount(sql);
        } catch (RuntimeException re) {
            throw re;
        }
    }

}
