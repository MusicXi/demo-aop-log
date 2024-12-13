package com.myron.ims.controller;


import com.zoe.datasource.dynamic.core.annotation.DynamicSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>标题: </p>
 * <p>描述: </p>
 * <p>版权: Copyright (c) 2024</p>
 * <p>公司: 智业软件股份有限公司</p>
 *
 * @version: 1.0
 * @author: linrongxi
 * @date: 2024-12-03
 */
@RestController
@RequestMapping("test")
public class TestDynamicDatasourceController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DynamicSource
    @PostMapping("create")
    public Map<String, Object> test() {
        Map<String, Object> result = new HashMap<>();
        result.put("data", jdbcTemplate.queryForList("select * from user"));
        return result;
    }
}
