package com.myron.ims;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.myron.ims.bean.Log;
import com.myron.ims.mapper.LogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.List;

@Component//被spring容器管理
@Order(1)//如果多个自定义ApplicationRunner，用来标明执行顺序
public class MyApplicationRunner implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyApplicationRunner.class);
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private TomcatServletWebServerFactory tomcatServletWebServerFactory;
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Log param = new Log();
        param.setTitle("测试数据");
        this.logMapper.update(param,new UpdateWrapper<Log>().lambda().isNotNull(Log::getLogId));
        List<Log> logList = this.logMapper.selectList(new QueryWrapper<>());
        logList.forEach(System.out::println);
        this.showUrl();
    }

    private void showUrl() throws Exception{
        String host = InetAddress.getLocalHost().getHostAddress();
        int port = tomcatServletWebServerFactory.getPort();
        String contextPath = tomcatServletWebServerFactory.getContextPath();
        LOGGER.info("欢迎访问 http://"+host+":"+port+contextPath+"/");
    }

}