package com.huanf.utils;

import com.alibaba.fastjson.JSON;
import com.huanf.ws.pojo.ResultMessage;

public class MessageUtils {

    /**
     * @param isSystemMessage 是否是系統消息。只要廣播消息才是系統消息。如果是私聊消息的話，就不是系統消息
     * @param fromName 發給誰消息，如果是系統消息的話，這個參數就不需要指定
     * @param message 消息的具體內容
     * @return
     */
    public static String getMessage(boolean isSystemMessage, String fromName, Object message) {

        ResultMessage result = new ResultMessage();
        result.setSystem(isSystemMessage);
        result.setMessage(message);
        if(fromName != null) {
            result.setFromName(fromName);
        }
        return JSON.toJSONString(result);
    }
}
