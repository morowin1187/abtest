package com.aiccj.abtest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@Slf4j
public abstract class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    protected Long getId(){
        Long accountId = (Long) request.getAttribute("id");
        return accountId;
    }

    protected String getName(){
        String name = (String) request.getAttribute("name");
        return name;
    }
}
