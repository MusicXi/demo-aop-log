# Docker image for springboot file run
# VERSION 0.0.1
# Author: zhangsan
FROM java:8
MAINTAINER zhangsan <zhangsan@gmail.com>
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp
# 将jar包添加到容器中并更名为app.jar
ADD demo-aop-log-1.0.0.jar app.jar
# EXPOSE：并不是真正的发布端口,告诉容器布署人员容器应该映射哪个端口给外界
EXPOSE 8099
# 运行jar包
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]