package com.zhgtrade.core;
import com.zhgtrade.chat.service.PrivateChatMsgStorage;
import com.zhgtrade.chat.util.SpringContextUtils;
import org.springframework.util.CollectionUtils;

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
public class SessionManager {

    // sessionId => [conn, conn]，同一个session，有多条连接，同一个浏览器，打开多个
    private static Map<String, Set<Session>> sessionConnections = new ConcurrentHashMap<>();

    // userId -> [sessionId, sessionId] 同一用户多端登录
    private static Map<String, Set<String>> userSessions = new ConcurrentHashMap<>();

    private static SessionManager sessionManager;
    private SessionManager() {
    }

    public static SessionManager getSessionManager() {
        if(sessionManager ==null){
            sessionManager = new SessionManager();
        }
        return sessionManager;
    }

    public Map<String, Set<Session>> getSessionConnections() {
        return sessionConnections;
    }


    private String getSessionId(Session session, String userId) {
        return session.getId()+"-"+userId;
    }

    public Set<Session> getUserSessions(String userId) {
        Set<String> sessions = userSessions.get(userId);
        if (sessions == null || sessions.size() == 0) {
            return null;
        }
        Set<Session> clients = new HashSet<>();
        sessions.forEach(sessionId -> {
            Set<Session> connections = sessionConnections.get(sessionId);
            if (connections != null) {
                clients.addAll(connections);
            }
        });
        return clients;
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
            SpringContextUtils.getBean(PrivateChatMsgStorage.class).updateCheckIsCustomServer(userId);
        }

    }

    public void addSession(Session session, String userId) {
        if (userId != null) {
            String sessionId = getSessionId(session, userId);
            Set<Session> connections = sessionConnections.get(sessionId);
            if (connections == null) {
                connections = new HashSet<>();
                sessionConnections.put(sessionId, connections);
            }
            connections.add(session);
            userLogin(session,userId);
            SpringContextUtils.getBean(PrivateChatMsgStorage.class).updateCheckIsCustomServer(userId);
        }

    }

}
