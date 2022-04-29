# 说明

## springboot版本
1. Filter记录每个web数据请求的requestId记录日志
2. WebLogAspect 记录web请求日志
    - 日志注解使用swagger方法的注解@ApiOperation
3. DaoLogAspect 记录web请求连接中的Mapper数据操作,通过reqeustId关联请求
    - 按表级别记录
    - 新增记录插入数据
    - 修改记录修改前的数据(高级生成对比日志)
    - 删除记录删除前的数据
4. 增加mysql和h2数据源来测试功能
5. 增加liquibase管理数据库脚本

## quick start
创建数据库
```sql
CREATE DATABASE `demo_aop_log` default CHARACTER set utf8 COLLATE utf8_unicode_ci;
```
- 启动方式:com.myron.ims.Application.main() 通过active来切换测试数据源
  - h2数据库: spring.profiles.active: dev-h2
  - mysql数据: spring.profiles.active: dev

## 联系    
项目详细设计说明:http://blog.csdn.net/myron_007/article/details/54927529
博客回复疑问不及时。如果有问题可以加临时群,439019717,把问题说明截图,贴下下去。解决完可以退出。

