package com.example.pptupload.controller;

import com.example.pptupload.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/auth")
@CrossOrigin
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        String token = userService.loginOrRegister(username, password);

        Map<String, Object> response = new HashMap<>();
        if (token != null) {
            response.put("code", 200);
            response.put("msg", "success");
            response.put("token", token);
        } else {
            response.put("code", 401);
            response.put("msg", "用户名或密码错误");
        }
        return response;
    }
}
