package com.aiccj.abtest.common.config;

import com.aiccj.abtest.common.CrossDomainFilter;
import com.aiccj.abtest.common.WebHandlerExceptionResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@Configuration
@Slf4j
public class CommonConfig {

    @Bean
    public WebHandlerExceptionResolver webHandlerExceptionResolver(){
        return new WebHandlerExceptionResolver(null);
    }

    @Bean
    public CrossDomainFilter crossDomainFilter() {
        return new CrossDomainFilter("*", "*", "*");
    }

    /**
     * 这里不开启也没问题 spring框架会自动帮我们注入bean 在数据源初始化之后
     * @param dataSource
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        log.info("[CommonConfig] [transactionManager] ========");
        return new DataSourceTransactionManager(dataSource);
    }
}
