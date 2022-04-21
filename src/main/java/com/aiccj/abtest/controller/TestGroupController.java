package com.aiccj.abtest.controller;

import com.aiccj.abtest.common.PageableEntity;
import com.aiccj.abtest.common.Resp;
import com.aiccj.abtest.mapper.TestGroupMapper;
import com.aiccj.abtest.model.TestGroup;
import com.aiccj.abtest.model.dto.TestGroupListReq;
import com.aiccj.abtest.model.dto.UserListReq;
import com.aiccj.abtest.model.User;
import com.aiccj.abtest.service.TestGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@RestController
@RequestMapping("/test/group")
public class TestGroupController extends BaseController{

    @Resource
    private TestGroupService testGroupService;

    @GetMapping
    public Resp<PageableEntity<TestGroup>> userList(TestGroupListReq req) {
        PageableEntity<TestGroup> list = testGroupService.list(req.getPageIndex(), req.getLimit());
        return Resp.createSuccessResp(list);
    }

    @PutMapping
    public Resp<TestGroup> create(@RequestBody TestGroup testGroup) {
        testGroup = testGroupService.create(testGroup, getName());
        return Resp.createSuccessResp(testGroup);
    }

    @PostMapping
    public Resp<TestGroup> edit(@RequestBody TestGroup testGroup) {
        testGroupService.edit(testGroup, getName());
        return Resp.createSuccessResp(testGroup);
    }
}
