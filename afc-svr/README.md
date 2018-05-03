# afc-svr

[TOC]

## 1. 部署

- 本机

```sh
cd ~/workspaces/02/wxx/afc-svr
./deploy/deploy-wblapp2.sh
```

## 2. 配置中心

在git服务器中加入 ```afc-svr-prod.yml``` 文件的内容

## 3. 创建容器并启动

```sh
docker run -d --net=host --name afc-svr-a -v /usr/local/afc-svr/a:/usr/local/myservice --restart=always nnzbz/spring-boot-app
```