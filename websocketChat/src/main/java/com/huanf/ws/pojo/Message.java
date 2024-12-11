package com.huanf.ws.pojo;

import lombok.Data;

/**
 * 用於封裝瀏覽器發送給服務端的消息數據
 */
@Data
public class Message {
    private String toName;
    private String message;
}
