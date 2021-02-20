package org.cold92.back.controller;

import com.auth0.jwt.JWT;
import org.cold92.back.bean.User;
import org.cold92.back.service.UserService;
import org.cold92.back.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 控制器
 * @author admin
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("login")
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            User user = userService.login(username, password);
            Map<String, String> payload = new HashMap<>();
            payload.put("username", user.getUsername());
            // 获取Token
            String token = JWTUtils.getToken(payload);
            resultMap.put("state", true);
            resultMap.put("msg", "login success");
            resultMap.put("token", token);
        } catch (Exception e) {
            resultMap.put("state", false);
            resultMap.put("msg", e.getMessage());
            resultMap.put("token", null);
        }
        return resultMap;
    }

    /**
     * 测试JWT拦截器的接口
     */
    @GetMapping("test")
    public void test() {
        System.out.println("This is test interface!");
    }
}
