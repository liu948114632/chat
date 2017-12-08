package com.zhgtrade.websocket;


import org.springframework.util.StringUtils;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.HashMap;
import java.util.Map;


/**
 * @author xxp
 * @version 2017- 12- 06 14:09
 * @description
 * @copyright www.zhgtrade.com
 */
public class CookieConfigurator extends ServerEndpointConfig.Configurator{

    @Override
    public void modifyHandshake(ServerEndpointConfig config,
                                HandshakeRequest request, HandshakeResponse response) {
        Map map = request.getHeaders();
        String cookie = null;
        if(map != null && map.get("cookie") != null){
            cookie = map.get("cookie").toString();
        }
        if (!StringUtils.isEmpty(cookie) && cookie.contains("uid")){
            config.getUserProperties().put("userid",  getUId(cookie));
        }else {
            config.getUserProperties().put("userid", "0");
        }
    }

    private String getUId(String cookie) {
        Map<String, String> cookieMap = parseCookie(cookie);
        String sid = (cookieMap.get("uid") != null ? cookieMap.get("uid") : "0");
        return sid;
    }

    private Map<String, String> parseCookie(String cookie) {
        Map<String, String> map = new HashMap<>();
        if (cookie != null) {
            cookie = cookie.replace("]","").replace("[","");
            String[] cookies = cookie.split("; ");
            for (String ck : cookies) {
                String[] kv = ck.split("=");
                if (kv.length == 2) {
                    map.put(kv[0], kv[1]);
                }
            }
        }
        return map;
    }
}
