package com.myron.ims.configuration;


import com.easycode8.datasource.dynamic.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.easycode8.datasource.dynamic.core.DynamicConnectionProxyFactory;
import com.easycode8.datasource.dynamic.core.DynamicDataSource;
import com.easycode8.datasource.dynamic.core.DynamicDataSourceProperties;
import com.easycode8.datasource.dynamic.core.provider.DataSourceProvider;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.List;


@PropertySource("classpath:META-INF/config/dynamic-datasource.properties")
@Import({DynamicDataSourceAutoConfiguration.class})
public class DynamicDatasourceMvcConfig implements WebMvcConfigurer {


    @Bean(name = "dynamicDataSource")
    public DynamicDataSource DataSource(Environment environment, DynamicDataSourceProperties dynamicDataSourceProperties, List<DataSourceProvider> dataSourceProviders, DynamicConnectionProxyFactory dynamicConnectionProxyFactory) {
        DataSourceProperties dataSourceProperties = Binder.get(environment).bind("spring.datasource", DataSourceProperties.class).orElse(null);
        DynamicDataSource dataSource = new DynamicDataSource(dataSourceProperties, dynamicDataSourceProperties, dataSourceProviders, dynamicConnectionProxyFactory);
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
