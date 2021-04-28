package com.greenleaf.fruitshop;

import com.greenleaf.fruitshop.model.service.IFruitCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {
    @Autowired
    private IFruitCateService bookCateService;
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FruitshopApplication.class);
    }

}
