package com.aiccj.abtest.controller;

import com.aiccj.abtest.common.Resp;
import com.aiccj.abtest.model.User;
import com.aiccj.abtest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController extends BaseController{

    @Autowired
    UserService userService;

    @GetMapping("/info")
    public Resp<User> login() {
        User user = userService.loadById(getId());
        return Resp.createSuccessResp(user);
    }
}
