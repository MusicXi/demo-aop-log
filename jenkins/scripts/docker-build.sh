#!/usr/bin/env bash
cd /var/jenkins_home/workspace/create_docker_images/target
cp ../jenkins/docker/Dockerfile ./
docker build -t linrx1/demo-aop-log:1.0 .