# 说明
当前项目用于学习测试spring aop日志功能。

正式开源版本已经发布:https://easycode8.github.io/easy-log

# 历史说明
## springboot版本说明
1. Filter记录每个web数据请求的requestId记录日志
2. WebLogAspect 记录web请求日志
    - 日志注解使用swagger方法的注解@ApiOperation(可以替换自己的注解测试功能)
3. DaoLogAspect 记录web请求连接中的Mapper数据操作,通过reqeustId关联请求
    - 按表级别记录
    - 新增记录插入数据
    - 修改记录修改前的数据(高级生成对比日志)
    - 删除记录删除前的数据
4. 增加mysql和h2数据源来测试功能
5. 增加liquibase管理数据库脚本

## quick start


- 启动运行:com.myron.ims.Application默认启动使用h2数据库,可以根据启动参数切换
  - h2数据库: -Dspring.profiles.active: dev-h2-plus
  - mysql数据: -Dspring.profiles.active: dev
    ```sql
    # 创建数据库
    CREATE DATABASE `demo_aop_log` default CHARACTER set utf8 COLLATE utf8_unicode_ci;
    ```
- 访问:http://127.0.0.1:9099

## 效果
![](./docs/img/demo.png)
## 联系    
项目详细设计说明:http://blog.csdn.net/myron_007/article/details/54927529
博客回复疑问不及时。如果有问题可以加临时群,439019717,把问题说明截图,贴下下去。解决完可以退出。
