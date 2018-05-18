# afc-scheduler-svr

[TOC]

## 1. 配置中心

在git服务器中加入 ```afc-scheduler-svr-prod.yml``` 文件的内容

## 2. 创建容器并启动

```sh
docker run -d --net=host --name afc-scheduler-svr -v /usr/local/afc-scheduler-svr:/usr/local/myservice --restart=always nnzbz/spring-boot-app
```