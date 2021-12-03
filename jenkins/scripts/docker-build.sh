#!/usr/bin/env bash

# 先删除之前的容器
docker ps -a | grep ${APP_NAME} | awk '{print $1}'| xargs docker rm -f
# 删除之前的镜像
docker rmi ${DOCKER_IMAGE_NAME}

# 构建新镜像
cd ${WORKSPACE}/target
cp ../jenkins/docker/Dockerfile ./
docker build -t ${DOCKER_IMAGE_NAME} .

# 打印当前镜像
echo "当前docker镜像:docker images | grep ${APP_NAME}"
docker images | grep ${APP_NAME}