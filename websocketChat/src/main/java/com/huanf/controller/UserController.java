package com.huanf.controller;

import com.huanf.pojo.Result;
import com.huanf.pojo.User;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("user")
public class UserController {

    /**
     * 登入
     * @param user 提交的用戶資訊，包含用戶名和密碼
     * @param session
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpSession session) {
        Result result = new Result();
        if(user != null && "123".equals(user.getPassword())) {
            result.setFlag(true);
            // 將數據存取到session對象中
            session.setAttribute("user",user.getUsername());
        } else {
            result.setFlag(false);
            result.setMessage("登入失敗");
        }
        return result;
    }

    /**
     * 獲取用戶名
     * @param session
     * @return
     */
    @GetMapping("/getUsername")
    public String getUsername(HttpSession session) {

        String username = (String) session.getAttribute("user");
        return username;
    }
}
