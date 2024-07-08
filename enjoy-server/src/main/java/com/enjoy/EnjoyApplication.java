package com.enjoy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching // 开启缓存
@EnableTransactionManagement //开启注解方式的事务管理
@Slf4j
public class EnjoyApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnjoyApplication.class, args);
        log.info("Server started. Open http://localhost/#/login in your browser to login.");
    }
}
