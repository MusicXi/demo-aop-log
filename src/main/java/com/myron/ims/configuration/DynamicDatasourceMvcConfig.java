package com.myron.ims.configuration;


import com.zoe.datasource.dynamic.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.zoe.datasource.dynamic.core.DynamicDataSource;
import com.zoe.datasource.dynamic.core.DynamicDataSourceProperties;
import com.zoe.datasource.dynamic.core.provider.DataSourceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.List;


@PropertySource("classpath:META-INF/config/dynamic-datasource.properties")
@Import({DynamicDataSourceAutoConfiguration.class})
public class DynamicDatasourceMvcConfig implements WebMvcConfigurer {


    @Bean(name = "dynamicDataSource")
    public DynamicDataSource DataSource(DynamicDataSourceProperties dataSourceProperties, List<DataSourceProvider> dataSourceProviders) {
        DynamicDataSource dataSource = new DynamicDataSource(dataSourceProperties, dataSourceProviders);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**等效于springmvc.xml 添加<mvc:default-servlet-handler/> //可访问前端静态资源*/
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
