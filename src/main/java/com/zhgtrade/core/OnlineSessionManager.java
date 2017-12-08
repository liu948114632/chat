package com.zhgtrade.core;

import com.zhgtrade.chat.service.PrivateChatMsgStorage;
import com.zhgtrade.chat.util.SpringContextUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.websocket.Session;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : xxp
 * Date： 2017-12-01 19:05
 */
public class OnlineSessionManager {

    private Map<String, Set<Session>> sessionConnections = new ConcurrentHashMap<>();

    private Map<String, Set<String>> userSessions = new ConcurrentHashMap<>();

    private static OnlineSessionManager onlineSessionManager;
    private OnlineSessionManager() {
    }

    public static OnlineSessionManager getOnlineSessionManager() {
        if(onlineSessionManager ==null){
            onlineSessionManager = new OnlineSessionManager();
        }
        return onlineSessionManager;
    }

    private String getSessionId(Session session, String userId) {
        return session.getId()+"-"+userId;
    }

    public int getUserSessions(String userId) {
        Set<String> sessions = userSessions.get(userId);
        if (sessions == null || sessions.size() == 0) {
            return 0;
        }
        int count = 0;
        for (String sessionId: sessions ) {
            Set<Session> connections = sessionConnections.get(sessionId);
            if (connections != null) {
                count ++;
            }
        }
        return count;
    }

    public void userLogin(Session session, String userId) {
        // 用户登录，新增用户session
        if (userId != null && userId.trim().length() > 0) {
            Set<String> sessions = userSessions.get(userId);
            if (sessions == null) {
                sessions = new HashSet<>();
                userSessions.put(userId, sessions);
            }
            sessions.add(getSessionId(session,userId));
        }
    }

    public void removeSession(Session session, String userId) {
        String sessionId = getSessionId(session,userId);
        if (sessionId != null) {
            Set<Session> connections = sessionConnections.get(sessionId);
            if (!CollectionUtils.isEmpty(connections)) {
                Iterator<Session> it = connections.iterator();
                while (it.hasNext()){
                    if(session.getId().equals((it.next()).getId())){
                        it.remove();
                    }
                }
                if (CollectionUtils.isEmpty(connections)){
                    sessionConnections.remove(sessionId);
                }
            }
        }
    }

    public void addSession(Session session, String userId) {
        if (!StringUtils.isEmpty(userId)) {
            String sessionId = getSessionId(session, userId);
            Set<Session> connections = sessionConnections.get(sessionId);
            if (connections == null) {
                connections = new HashSet<>();
                sessionConnections.put(sessionId, connections);
            }
            connections.add(session);
            userLogin(session,userId);
            String[] uids = userId.split("_");
            SpringContextUtils.getBean(PrivateChatMsgStorage.class).checkIsRead(Integer.parseInt(uids[0]),Integer.parseInt(uids[1]));
        }

    }

}
