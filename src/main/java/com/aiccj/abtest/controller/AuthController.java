package com.aiccj.abtest.controller;

import com.aiccj.abtest.common.Resp;
import com.aiccj.abtest.model.User;
import com.aiccj.abtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService accountService;

    @PostMapping("/login")
    public Resp<String> login(@RequestBody User account) {
        return Resp.createSuccessResp(accountService.login(account));
    }

    @PostMapping("/logout")
    public Resp<String> logout() {
        return Resp.createSuccessResp();
    }
}
