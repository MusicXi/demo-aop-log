CREATE TABLE `sys_log` (
  `log_id` varchar(32)  NOT NULL COMMENT '日志主键',
  `type` varchar(20) DEFAULT NULL COMMENT '日志类型',
  `title` varchar(100) DEFAULT NULL COMMENT '日志标题',
  `ip` varchar(20) DEFAULT NULL COMMENT '请求IP',
  `request_uri` varchar(300) DEFAULT NULL COMMENT 'URI',
  `method` varchar(300) DEFAULT NULL COMMENT '请求方式',
  `params` text COMMENT '提交参数',
  `exception` text COMMENT '异常',
  `operate_date` timestamp(6) NULL DEFAULT NULL COMMENT '操作时间',
  `timeout` varchar(10) DEFAULT NULL COMMENT '请求时长',
  `login_name` varchar(32) DEFAULT NULL COMMENT '用户登入名',
  `request_id` varchar(32) DEFAULT NULL COMMENT 'requestID',
  `data_snapshot` text COMMENT '历史数据',
  `request_timestamp` bigint(32) DEFAULT NULL COMMENT '请求时间戳',
  `status` int(11) DEFAULT NULL COMMENT '日志状态',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT;
