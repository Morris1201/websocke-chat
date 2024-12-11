package com.huanf.ws.pojo;

import lombok.Data;

/**
 * 用來封裝服務端給瀏覽器發送的消息數據
 */
@Data
public class ResultMessage {

    private boolean isSystem;
    private String fromName;
    private Object message;//如果是系統消息數據
}
