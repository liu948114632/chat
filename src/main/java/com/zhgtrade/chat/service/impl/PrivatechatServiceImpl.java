package com.zhgtrade.chat.service.impl;


import com.zhgtrade.chat.service.PrivatechatService;
import com.zhgtrade.dao.PrivatechatDao;
import com.zhgtrade.model.Privatechat;
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
public class PrivatechatServiceImpl implements PrivatechatService {

    @Autowired
    private PrivatechatDao privatechatDao;

    @Override
    public Privatechat insert(Privatechat privatechat) {
        privatechatDao.save(privatechat);
        return privatechat;
    }

    @Override
    public List<Map> getPrivatechat(int userId,int size,int pageNum) {
        String sql = "SELECT id,touserid as sendUserId,update_time as updateTime,online,num " +
                " from privatechat where userid =" +userId+
                " order by update_time desc" ;
        return privatechatDao.findByParam(size, pageNum, sql, true);
    }

    @Override
    public void update(Privatechat privatechat) {
       this.privatechatDao.update(privatechat);
    }

    @Override
    public int findByParamCount(int userId){
        try {
            String sql = "SELECT count(0) from privatechat where userid =" +userId ;
            return privatechatDao.findByParamCount(sql);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public int findChatMsgCount(int userId){
        try {
            String sql = "SELECT SUM(num) from privatechat where userid=" +userId ;
            return privatechatDao.findByParamCount(sql);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public Privatechat getChatByParam(int sendUserid, int receiveUserId){
        try {
            Privatechat privatechat = new Privatechat();
            privatechat.setUserid(sendUserid);
            privatechat.setTouserid(receiveUserId);
            return privatechatDao.getChatByParam(privatechat);
        } catch (RuntimeException re) {
            throw re;
        }
    }

}
