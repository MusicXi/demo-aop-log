CREATE TABLE `sys_log` (
  `log_id` varchar(32)  NOT NULL COMMENT '日志主键',
  `type` varchar(20) DEFAULT NULL COMMENT '日志类型',
  `title` varchar(100) DEFAULT NULL COMMENT '日志标题',
  `description` varchar(500) DEFAULT NULL COMMENT '日志描述',
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

CREATE TABLE `sys_user` (
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `organization_id` varchar(32) DEFAULT NULL COMMENT '机构ID',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `phone` varchar(100) DEFAULT NULL COMMENT '手机',
  `email` varchar(100) DEFAULT NULL COMMENT '邮件',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人ID',
  `locked` int(2) DEFAULT NULL COMMENT '是否被锁定',
  `login_ip` varchar(20) DEFAULT NULL COMMENT '最后登入IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登入日期',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


