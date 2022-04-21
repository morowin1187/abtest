package com.aiccj.abtest;

import com.aiccj.abtest.common.ExceptionPrinter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@SpringBootApplication
@EnableTransactionManagement
public class AbtestApplication {

    public static void main(String[] args) {
        System.setProperty(ExceptionPrinter.packagePrefixName, "com.aiccj.");
        SpringApplication.run(AbtestApplication.class, args);
    }

}
