package com.myron.ims.configuration;


import com.easycode8.easylog.autoconfigure.EasyLogAutoConfiguration;
import com.easycode8.easylog.core.annotation.EasyLogProperties;
import com.easycode8.easylog.web.autoconfigure.EasyLogWebAutoConfiguration;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:META-INF/config/easy-log.properties")
@Import({EasyLogAutoConfiguration.class, EasyLogWebAutoConfiguration.class})
public class EasyLogConfig implements WebMvcConfigurer {

    @Bean
    public EasyLogProperties easyLogProperties(Environment environment) {
        EasyLogProperties properties = Binder.get(environment).bind("spring.easy-log", EasyLogProperties.class).orElse(null);
        return properties;
    }

    /**等效于springmvc.xml 添加<mvc:default-servlet-handler/> //可访问前端静态资源*/
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
