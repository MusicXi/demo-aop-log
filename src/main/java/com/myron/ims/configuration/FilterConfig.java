package com.myron.ims.configuration;

import com.myron.ims.filter.HttpRequestMDCFilter;
import com.myron.ims.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * filter order的数值越小 则优先级越高
 * @author myron
 */
@Configuration
public class FilterConfig {
    @Bean
    public HttpRequestMDCFilter httpRequestMDCFilter() {
        return new HttpRequestMDCFilter();
    }

    @Bean
    public LoginFilter loginFilter() {
        return new LoginFilter();
    }


    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(httpRequestMDCFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter("mappedHeaders", "true");
        registrationBean.addInitParameter("mappedParameters", "true");
        registrationBean.addInitParameter("mappedCookies", "true");
        registrationBean.setOrder(1);
        registrationBean.setName("httpRequestMDCFilter[日志记录]");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean loginFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(loginFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter("excludedPaths", "/,/login,*.css,*.js,*.ico,*.ttf,*.woff,*.map");
        registrationBean.setOrder(2);
        registrationBean.setName("loginFilter[身份认证]");
        return registrationBean;
    }
}
