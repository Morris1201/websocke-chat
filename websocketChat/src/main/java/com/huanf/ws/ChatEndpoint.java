package com.huanf.ws;

import com.alibaba.fastjson.JSON;
import com.huanf.config.GetHttpSessionConfig;
import com.huanf.utils.MessageUtils;
import com.huanf.ws.pojo.Message;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfig.class)
@Component
public class ChatEndpoint {

    private static final Map<String, Session> onLineUsers = new ConcurrentHashMap<>();
    private HttpSession httpSession;

    /**
     * 建立websocket連接後，被調用
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        // 1.將session進行保存
        httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        String user = (String) httpSession.getAttribute("user");
        onLineUsers.put(user, session);

        // 2.廣播消息，需要將登入的所有的用戶推送給所有的用戶
        String message = MessageUtils.getMessage(true, null, getFriends());
        broadcastAllUsers(message);
    }

    public Set getFriends() {
        Set<String> set = onLineUsers.keySet();
        return set;
    }

    private void broadcastAllUsers(String message) {
        try {
            // 遍歷map集合
            Set<Map.Entry<String, Session>> entries = onLineUsers.entrySet();
            for (Map.Entry<String, Session> entry : entries) {
                // 獲取到所有用戶對應的session對象
                Session session = entry.getValue();
                // 發送消息
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 紀錄日誌
        }
    }

    /**
     * 瀏覽器發送消息到服務端，該方法被調用
     * 小王 ----> 大明 (發送給)
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        try {
            // 將消息推送給指定的用戶
            Message parsedMessage = JSON.parseObject(message, Message.class);
            // 獲取 消息接收方的用戶名
            String recipientUsername = parsedMessage .getToName();
            String toMessage = parsedMessage .getMessage();

            // 獲取消息接收方用戶對象的session對象
            Session session = onLineUsers.get(recipientUsername);
            String user = (String) httpSession.getAttribute("user");
            String fromMessage = MessageUtils.getMessage(false, user, toMessage);
            session.getBasicRemote().sendText(fromMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 斷開websocket連接時被調用
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        // 1.從onLineUsers中剔除當前用戶的session對象
        String user = (String) httpSession.getAttribute("user");
        onLineUsers.remove(user);

        // 2.通知其它所有的用戶，當前用戶下線了
        String message = MessageUtils.getMessage(true, null, getFriends());
        broadcastAllUsers(message);
    }
}
