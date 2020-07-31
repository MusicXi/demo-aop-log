package com.myron.ims.factory.log;

import com.myron.ims.mapper.LogMapper;
import com.myron.ims.service.LogService;
import com.myron.ims.service.impl.LogServiceImpl;

/**
 * Created by linrx1 on 2019/12/9.
 */
public class LogRecorderFactory {

    public LogService productLogService(String type) {
         return new LogServiceImpl();
    }
}
