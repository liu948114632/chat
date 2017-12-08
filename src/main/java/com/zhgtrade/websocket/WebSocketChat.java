package com.zhgtrade.websocket;

import com.zhgtrade.chat.service.PrivateChatMsgStorage;
import com.zhgtrade.chat.util.SpringContextUtils;
import com.zhgtrade.core.SessionManager;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
@Component
@ServerEndpoint(value = "/webSocketChat/{param}",configurator = CookieConfigurator.class)
public class WebSocketChat {

	private static SessionManager sessionManager = SessionManager.getSessionManager();

	@OnOpen
	public void onOpen(Session session, @PathParam(value="param") String userId, EndpointConfig config)throws Exception{
//		if(userId.equals(config.getUserProperties().get("userid").toString())){
			sessionManager.addSession(session,userId);
//		}else {
//			throw new Exception();
//		}
	}

	@OnClose
	public void onClose(@PathParam(value="param") String userId, Session session){
		sessionManager.removeSession(session, userId);
	}

	@OnMessage
	public void onMessage(String jsonMessage,@PathParam(value="param") String userId ) {
		SpringContextUtils.getBean(PrivateChatMsgStorage.class).ckeckMsg(userId,jsonMessage);
	}

	@OnError
	public void onError(Session session, Throwable error){
		error.printStackTrace();
	}

	public static void sendMessage(String userId, String message){
		try {
			Set<Session> sessions = sessionManager.getUserSessions(userId);
			for (Session session : sessions) {
				if(session != null){
					session.getBasicRemote().sendText(message);
				}
			}
		} catch (IOException e) {
		}
	}


}
