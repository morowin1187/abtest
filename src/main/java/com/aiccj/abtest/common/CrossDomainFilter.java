package com.aiccj.abtest.common;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

/**
 * 跨域过滤器
 */
@Slf4j
public class CrossDomainFilter implements Filter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private String allowOrigin;

    private String allowMethods;

    private String allowHeaders;

    public CrossDomainFilter(String allowOrigin, String allowMethods, String allowHeaders) {
        this.allowOrigin = allowOrigin;
        this.allowMethods = allowMethods;
        this.allowHeaders = allowHeaders;
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE + 1;
    }

    @Override
    public void doFilter(ServletRequest request1, ServletResponse response1, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) request1;
        HttpServletResponse response = (HttpServletResponse) response1;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");

        /**cors modified start**/
        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaders("Access-Control-Request-Headers");
        if(Objects.nonNull(headerNames)) {
            while (headerNames.hasMoreElements()) {
                headers.append(headerNames.nextElement()).append(",");
            }
        }
        response.setHeader("Access-Control-Allow-Headers", headers.toString());
        /**cors modified end**/

        if (HttpMethod.OPTIONS.toString().equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("-----全局跨域过滤器 {} " +
            "初始化完成！-----", this.getClass().getName());
    }

    @Override
    public void destroy() {
        logger.info("----全局跨域过滤器 {} " +
            "销毁完成！----", this.getClass().getName());
    }
}
