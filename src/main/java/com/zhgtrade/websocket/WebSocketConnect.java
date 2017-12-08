package com.zhgtrade.websocket;


import com.zhgtrade.core.OnlineSessionManager;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/webSocketConnect/{param}",configurator = CookieConfigurator.class)
public class WebSocketConnect {
	private OnlineSessionManager onlineSessionManager = OnlineSessionManager.getOnlineSessionManager();

	@OnOpen
	public void onOpen(Session session, @PathParam(value="param") String userId, EndpointConfig config) throws Exception{
		if(userId.split("_")[0].equals(config.getUserProperties().get("userid").toString())){
			onlineSessionManager.addSession(session,userId);
		}else {
			throw new Exception();
		}
	}

	@OnClose
	public void onClose(Session session, @PathParam(value="param") String userId){
		onlineSessionManager.removeSession(session, userId);
	}

	@OnMessage
	public void onMessage(String jsonMessage, Session session) {
	}


	@OnError
	public void onError(Session session, Throwable error){
		error.printStackTrace();
	}



}
