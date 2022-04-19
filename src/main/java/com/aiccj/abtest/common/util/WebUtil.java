package com.aiccj.abtest.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@Slf4j
public class WebUtil {



    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return "unknown ip";
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    public static String getHeader(String key){
        return getRequest().getHeader(key);
    }

    public static Integer getIntHeader(String key){
        return getRequest().getIntHeader(key);
    }

    public static void write(byte[] bytes) throws IOException {
        ServletOutputStream outputStream = getResponse().getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

}
