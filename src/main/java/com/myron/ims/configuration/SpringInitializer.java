package com.myron.ims.configuration;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.WebApplicationContext;

public class SpringInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 在这里执行Spring初始化的逻辑
//        System.out.println("Spring initialization logic goes here.");

        if (event.getApplicationContext() instanceof WebApplicationContext) {
            WebApplicationContext webApplicationContext = (WebApplicationContext) event.getApplicationContext();
            if (webApplicationContext.getParent() == null) {
                // 在这里执行Spring初始化的逻辑
                System.out.println("项目初始化启动");
            }
        }
    }
}