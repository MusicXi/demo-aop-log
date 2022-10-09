package com.myron.ims.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = AppProperties.PROPERTY_PREFIX)
public class AppProperties {
    public static final String PROPERTY_PREFIX = "app";
    /**api的虚拟前缀,用于k8s环境下,前端请求后端,增加虚拟前缀用于ingress用于同域名识别转发,如/log/. 本地开发模式不必设置
     * -Dapp.api-prefix=/log/
     * */
    private String apiPrefix = "/";

    public String getApiPrefix() {
        return apiPrefix;
    }

    public void setApiPrefix(String apiPrefix) {
        this.apiPrefix = apiPrefix;
    }
}
