package com.aiccj.abtest.common;

import com.aiccj.abtest.common.exception.BusinessException;
import com.aiccj.abtest.common.exception.BusinessMsg;
import com.aiccj.abtest.common.token.TokenManager;
import com.aiccj.abtest.common.util.WebUtil;
import com.aiccj.abtest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@Aspect
@Order(5)
@Component
@Slf4j
public class AuthAspect {

    private static List<String> ignoreUrls = new ArrayList();

    private static List<String> clearToken = new ArrayList();

    private static String SYS_URL_PRE = "/auth/";
    private static String ADMIN_URL_PRE = "/admins/";

    static {
        clearToken.add("/auth/logout");
    }

    @Autowired
    UserService accountService;

    @Pointcut("execution(* com.aiccj.abtest.controller..*.*(..))")
    public void controllerLog() {
    }

    @Before("controllerLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null)
            return;
        HttpServletRequest request = attributes.getRequest();
        String method = request.getMethod();
        String ip = WebUtil.getIpAddr(request);
        String requestURI = request.getRequestURI();
        log.info("IP:{} 请求 {}:{}", ip, method, request.getRequestURL());

        if (ignoreUrls.contains(request.getRequestURI())
                || request.getRequestURI().startsWith(SYS_URL_PRE)) {
            if (clearToken.contains(requestURI)) {
                accountService.logout(request.getHeader("AuthToken"));
            }
        } else {
            String token = request.getHeader("AuthToken");
            TokenManager.TokenDetail tokenDetail = accountService.validateToken(token);
            request.setAttribute("id", tokenDetail.getId());
            request.setAttribute("name", tokenDetail.getName());
            request.setAttribute("type", tokenDetail.getType());
            request.setAttribute("role", tokenDetail.getRole());
            if (request.getRequestURI().startsWith(ADMIN_URL_PRE)
                    && (!Constant.ROLE_ADMIN.equals(tokenDetail.getRole()))) {
                throw new BusinessException(BusinessMsg.URI_ERROR);
            }
            log.info("{}(accountId {}) 请求{}:{}", tokenDetail.getName(), tokenDetail.getId(), method, request.getRequestURL());
        }
    }

    @AfterReturning(returning = "ret", pointcut = "controllerLog()")
    public void doAfterReturning(Object ret) throws Throwable {

    }
}
