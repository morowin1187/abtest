package com.aiccj.abtest.common.config;

import com.aiccj.abtest.common.WebHandlerExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@Configuration
public class CommonConfig {

    @Bean
    public WebHandlerExceptionResolver webHandlerExceptionResolver(){
        return new WebHandlerExceptionResolver(null);
    }
}
