package com.huanf.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        // 獲取HttpSession對象
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        // 將HttpSession對象保存起來
        sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
    }
}