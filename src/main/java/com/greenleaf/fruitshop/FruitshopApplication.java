package com.greenleaf.fruitshop;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.greenleaf.fruitshop.model.mapper")
public class FruitshopApplication {
    public static void main(String[] args) {
        SpringApplication.run(FruitshopApplication.class, args);
    }

}
