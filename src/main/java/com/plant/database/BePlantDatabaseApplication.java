package com.plant.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author jingyu chen
 */
@SpringBootApplication
@MapperScan(basePackages = "com.plant.database.mapper")
public class BePlantDatabaseApplication implements ApplicationListener<ApplicationReadyEvent> {



    public static void main(String[] args) {
        SpringApplication.run(BePlantDatabaseApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

    }
}
