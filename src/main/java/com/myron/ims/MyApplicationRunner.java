package com.myron.ims;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.myron.ims.bean.Log;
import com.myron.ims.mapper.LogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component//被spring容器管理
@Order(1)//如果多个自定义ApplicationRunner，用来标明执行顺序
public class MyApplicationRunner implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyApplicationRunner.class);
    @Autowired
    private LogMapper logMapper;
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.out.println(("----- this.logMapper.selectList method test ------"));
        List<Log> logList = this.logMapper.selectList(new QueryWrapper<>());
        logList.forEach(System.out::print);
    }

}