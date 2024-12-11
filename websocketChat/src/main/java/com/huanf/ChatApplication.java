package com.huanf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    /**
     * 1、登入的用戶名是任意的，密碼是123。
     *
     * 用戶登入成功之後，會將session資料（也就是用戶資料），其中寫死"user"字串作為key存入GetHttpSessionConfig類別的ServerEndpointConfig集合（是Map集合）裡面，
     * 然後在ChatEndpoint類別，根據"user"字串取出該用戶對應的session資料，把這個session資料再存入httpSession變數，
     * 然後從httpSession變數裡面再存入onlineUsers變數，此時onlineUsers變數裡面有資料的話，就說明有在線用戶，就會通過'系統通知'在線用戶列表資料傳給前端。
     *
     * 2、可以跟在線的用戶進行私聊，雙方的資料是通過'非系統通知'在線用戶列表資料傳給前端。
     *
     * 3、運行啟動類，使用兩個不同瀏覽器分別訪問 http://localhost/login.html，密碼123，用戶名稱任意，然後就可以聊天了。
     *
     * 4、沒有資料庫，資料是存儲在請求內存裡的。刷新網頁之後資料還在，但是重啟SpringBoot之後資料就沒了。
     */

}
