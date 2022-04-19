package com.aiccj.abtest.controller;

import com.aiccj.abtest.common.PageableEntity;
import com.aiccj.abtest.common.Resp;
import com.aiccj.abtest.model.dto.UserListReq;
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
@RequestMapping("/admins")
@Slf4j
public class AdminController extends BaseController{

    @Autowired
    UserService userService;

    @GetMapping("/info")
    public Resp<String> info() {
        return Resp.createSuccessResp("你有管理员权限");
    }

    @GetMapping("/user/list")
    public Resp<PageableEntity<User>> userList(UserListReq req) {
        PageableEntity<User> list = userService.list(req.getPageIndex(), req.getLimit());
        return Resp.createSuccessResp(list);
    }

    @PutMapping("/user")
    public Resp<User> addUser(@RequestBody User userReq) {
        User user = userService.createUser(userReq);
        return Resp.createSuccessResp(user);
    }

    @PostMapping("/user")
    public Resp<User> editUser(@RequestBody User userReq) {
        User user = userService.editUser(userReq);
        return Resp.createSuccessResp(user);
    }

}
