#!/usr/bin/env bash
# 使用当前构建的镜像启动
docker run -d -p 8088:8088 --name ${APP_NAME} ${DOCKER_IMAGE_NAME}

echo "打印当前容器"
# 打印当前容器
docker ps -a | grep  ${DOCKER_IMAGE_REPOSITORY_PREFIX}