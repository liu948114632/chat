package com.zhgtrade.chat.service;

import com.alibaba.fastjson.JSONObject;
import com.zhgtrade.bean.MsgBean;
import com.zhgtrade.chat.util.JsonUtils;
import com.zhgtrade.core.OnlineSessionManager;
import com.zhgtrade.core.SessionManager;
import com.zhgtrade.dao.ChatCustomServerDao;
import com.zhgtrade.model.ChatCustomServer;
import com.zhgtrade.model.Fuser;
import com.zhgtrade.model.Privatechat;
import com.zhgtrade.model.PrivatechatMsg;
import com.zhgtrade.websocket.WebSocketChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author xxp
 * @version 2017- 09- 26 19:12
// * @description
 * @copyright www.zhgtrade.com
 */
@Component
public class PrivateChatMsgStorage {

    private static Queue<PrivatechatMsg> queues = new ConcurrentLinkedQueue<>();
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8);
    @Autowired
    private PrivatechatMsgService privatechatMsgService;
    @Autowired
    private ChatUserService chatUserService;
    @Autowired
    private PrivatechatService privatechatService;
    @Autowired
    private ChatCustomServerDao chatCustomServerDao;

    //消息组装类，统一写到外面节省更多栈空间
    private Map resultMap = null;
    private PrivatechatMsg privatechatMsg = null;
    private Fuser fuser = null;

    private OnlineSessionManager onlineSessionManager = OnlineSessionManager.getOnlineSessionManager();

    private SessionManager sessionManager = SessionManager.getSessionManager();

    @PostConstruct
    public void work(){
        for (int i = 0; i < 8; i++) {
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    try {
                        PrivatechatMsg privatechatMsg = null;
                        while (true){
                            if((privatechatMsg = queues.poll()) != null){
                                saveMsg(privatechatMsg);
                            }
                            Thread.sleep(100);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 消息接受校验方法，后续可加入禁言用户
     * @param userId
     * @param jsonMsg
     */
    public void ckeckMsg(String userId,String jsonMsg){
        try {
            Fuser user = this.chatUserService.getUserInfo(Integer.valueOf(userId));
            if(user == null){
                sendMessage(-1,null,userId);//用户信息不正确
                return ;
            }
            MsgBean msgBean =  JsonUtils.jsonToPojo(jsonMsg,MsgBean.class);
            if(!userId.equals(msgBean.getSendUserId()+"")){
                sendMessage(-1,null,userId);
                return ;
            }
            Fuser user2 = this.chatUserService.getUserInfo(msgBean.getReceiveUserId());
            if(user2 == null){
                sendMessage(-2,null,userId);//对方用户信息不存在
                return ;
            }
            privatechatMsg = new PrivatechatMsg();
            privatechatMsg.setContent(msgBean.getMsgContent());
            privatechatMsg.setSendUserId(user.getFid());
            privatechatMsg.setReceiveUserId(user2.getFid());
            privatechatMsg.setCreateTime(new Timestamp(new Date().getTime()));
            privatechatMsg.setStatus(1);
            privatechatMsg.setType(msgBean.getType());

            queues.add(privatechatMsg);
            //发送方
            privatechatMsg.setNickName(user.getFnickName());
            privatechatMsg.setNickName2(user2.getFnickName());
            privatechatMsg.setEmail(user2.getFemail());
            privatechatMsg.setPhone(user2.getFtelephone());
            privatechatMsg.setRank(user2.isRank());
            sendMessage(200,privatechatMsg,privatechatMsg.getSendUserId()+"");
            //接收方
            privatechatMsg.setNickName(user2.getFnickName());
            privatechatMsg.setNickName2(user.getFnickName());
            privatechatMsg.setEmail(user.getFemail());
            privatechatMsg.setPhone(user.getFtelephone());
            privatechatMsg.setRank(user.isRank());
            sendMessage(200,privatechatMsg,privatechatMsg.getReceiveUserId()+"");
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage(500,null,userId);
        }
    }

    /**
     * 统一发送消息
     * @param code
     * @param data
     * @param userId
     */
    private void sendMessage(int code,Object data,String userId){
        try {
            resultMap = new HashMap<>();
            resultMap.put("code",code);
            resultMap.put("data",data);
            WebSocketChat.sendMessage(userId, new JSONObject(resultMap).toString());
        } catch (Exception e) {
        }
    }

    /**
     * 保存消息
     * @param privatechatMsg
     */
    private void saveMsg(PrivatechatMsg privatechatMsg){
        try {
            privatechatMsgService.insertMsg(privatechatMsg);
            checkIsRead(privatechatMsg.getSendUserId(),privatechatMsg.getReceiveUserId());
            checkIsRead(privatechatMsg.getReceiveUserId(),privatechatMsg.getSendUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查消息是否读取，并且更新会话列表数据
     * @param userId
     * @param toUserId
     */
    public void checkIsRead(int userId,int toUserId){
        int status = 1;
        Set set =  sessionManager.getUserSessions(toUserId+"");
        if(CollectionUtils.isEmpty(set)){
            status = 2;
        }
        int count = onlineSessionManager.getUserSessions(userId+"_"+toUserId);
        Privatechat privatechat = this.privatechatService.getChatByParam(userId,toUserId);
        if(privatechat == null){
            privatechat = new Privatechat();
            privatechat.setCreateTime(new Timestamp(new Date().getTime()));
            privatechat.setUpdateTime(new Timestamp(new Date().getTime()));
            privatechat.setUserid(userId);
            privatechat.setTouserid(toUserId);
            privatechat.setNum(count >= 0 ? 0 : 1);
            privatechat.setOnline(status);
            this.privatechatService.insert(privatechat);
        }else{
            if(count > 0){
                privatechat.setNum(0);
            }else{
                privatechat.setNum(privatechat.getNum()+1);
            }
            privatechat.setStatus(status);
            privatechat.setOnline(status);
            privatechat.setUpdateTime(new Timestamp(new Date().getTime()));
            this.privatechatService.update(privatechat);
        }
    }

    /**
     * 判断是否为客服
     * @param userId
     */
    public void updateCheckIsCustomServer(String userId){
        try {
            fuser = this.chatUserService.getUserInfo(Integer.parseInt(userId));
            if (fuser != null && fuser.isRank()){
                List<ChatCustomServer> list = chatCustomServerDao.findByProperty("userid",Integer.parseInt(userId));
                if (!CollectionUtils.isEmpty(list)){
                    ChatCustomServer customServer = list.get(0);
                    Set set =  sessionManager.getUserSessions(userId);
                    if(CollectionUtils.isEmpty(set)){
                        customServer.setOnline(2);
                    }else {
                        customServer.setOnline(1);
                    }
                    customServer.setUpdateTime(new Timestamp(new Date().getTime()));
                    this.chatCustomServerDao.update(customServer);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

}


