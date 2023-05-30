# 说明
>easy-log开源版本已经发布:https://easycode8.github.io/easy-log

本项目验证springmvc项目接入easy-log的可行性

# 步骤
## 1. 导入maven依赖
- easy-log-spring-boot-starter 提供基础的日志动态记录
- easy-log-web 提供web在线管理
- spring-boot-autoconfigure springmvc没有默认加入自动装配的类,因为easy-log框架是用springboot starter封装的,因此需要补充,注意版本要适配
```xml
		<!--测试easy-log日志框架-->
		<dependency>
			<groupId>io.github.easycode8</groupId>
			<artifactId>easy-log-spring-boot-starter</artifactId>
			<version>1.0.3</version>
		</dependency>
		<!--测试easy-log日志框架web在线管理功能-->
		<dependency>
			<groupId>io.github.easycode8</groupId>
			<artifactId>easy-log-web</artifactId>
			<version>1.0.3</version>
		</dependency>

		<!--定位当前spring版本(5.0.5.RELEASE)对应的springboot的autoconfigure-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-autoconfigure</artifactId>
			<version>2.0.0.RELEASE</version>
		</dependency>
```

## 2. 创建配置类
springmvc不会自动装配,因此需要自己定义配置类
1. 配置类实现WebMvcConfigurer 开启默认的静态资源解析,用于访问easy-log-web中的资源
2. 导入easy-log-spring-boot-starter中的EasyLogAutoConfiguration 和 easy-log-web的EasyLogWebAutoConfiguration
3. 导入easy-log相关配置属性(属性名称自定义如:easy-log.properties),并绑定EasyLogProperties(非springboot项目不会自动注入属性)

```properties
spring.easy-log.async=false
spring.easy-log.scan-swagger.enabled=true
spring.easy-log.scan-service.enabled=false
spring.easy-log.scan-controller.enabled=true
spring.easy-log.scan-mybatis-plus.enabled=true
spring.easy-log.operator=SESSION.ims_user.username
```
```java
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
```
## 3. 配置加入项目的mvc的xml配置
> 注意必须是mvc的xml加入bean定义,因为EasyLogWebAutoConfiguration导入controller必须在springmvc容器中实现,不然会找不到控制器接口

举例为当前项目mvc配置为spring-mvc.xml,补充以下定义
```xml
    <!-- easy-log-web的在线页面资源以webjars方式提供 因此要提供/webjars/**资源映射-->
    <mvc:resources location="classpath:/META-INF/resources/webjars/" mapping="/webjars/**"/>
    <!--上面的手动配置easy-log的配置-->
    <bean class="com.myron.ims.configuration.EasyLogConfig"></bean>
```

## 4. 其他注意事项
easy-log有自己日志处理线程池,项目中如果原先由定义自己线程池使用@Autowired 需要指定自己具体的线程池如:@Qualifier("taskExecutor")

```java
	@Autowired
	@Qualifier("taskExecutor")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

```