package com.zhgtrade.chat.service.impl;

import com.zhgtrade.chat.service.ChatUserService;
import com.zhgtrade.dao.FuserDAO;
import com.zhgtrade.model.Fuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xxp
 * @version 2017- 12- 04 12:56
 * @description
 * @copyright www.zhgtrade.com
 */
@Service
public class ChatUserServiceImpl implements ChatUserService {
    @Autowired
    private FuserDAO fuserDAO;

    private Map<String,Map> userMap = new HashMap();

    @Override
    public Fuser getUserInfo(int userid) {
        try{
            Map map = userMap.get(userid+"");
            Date nowDate = null;
            if(map != null ){
                nowDate = (Date) map.get("validtime");
            }
            if(nowDate != null && (new Date().getTime() - nowDate.getTime())  < 5 * 60 * 1000){
                return (Fuser)(userMap.get(userid+"").get(userid+""));
            }else{
                map = new HashMap<>();
                map.put(userid+"",this.fuserDAO.findById(userid));
                map.put("validtime",new Date());
                userMap.put(userid+"",map);
                return (Fuser)(userMap.get(userid+"").get(userid+""));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
