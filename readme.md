### 说明
- 启动方式:com.myron.ims.Application.main()

项目详细设计说明:http://blog.csdn.net/myron_007/article/details/54927529
博客回复疑问不及时。如果有问题可以加临时群,439019717,把问题说明截图,贴下下去。解决完可以退出。

#### springboot版本
1. Filter记录每个web数据请求的requestId记录日志
2. WebLogAspect 记录web请求日志
    - 日志注解使用swagger方法的注解@ApiOperation
3. DaoLogAspect 记录web请求连接中的Mapper数据操作,通过reqeustId关联请求
    - 按表级别记录
    - 新增记录插入数据
    - 修改记录修改前的数据(高级生成对比日志)
    - 删除记录删除前的数据