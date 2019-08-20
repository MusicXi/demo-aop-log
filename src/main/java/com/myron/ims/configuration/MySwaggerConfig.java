package com.myron.ims.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 //必须存在
@EnableWebMvc //必须存在
public class MySwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.any()).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("Lin rx", "https://dddd", "792331407@qq.com");
		return new ApiInfoBuilder()
			.title("AOP日志测试项目接口文档")
			.description("XXX项目接口测试")
			.version("1.0.0")
			.contact(contact)
			.termsOfServiceUrl("")
			.license("")
			.licenseUrl("").build();
	}
}