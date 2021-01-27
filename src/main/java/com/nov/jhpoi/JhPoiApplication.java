package com.nov.jhpoi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.nov.jhpoi.sql.mapper")
public class JhPoiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JhPoiApplication.class, args);
    }

}
