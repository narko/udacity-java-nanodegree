#!/bin/bash

echo "Starting mysql-docker-container"

sudo docker run -d \
      -p 2012:3306 \
     --name mysql-docker-container \
     -e MYSQL_ROOT_PASSWORD=root123 \
     -e MYSQL_DATABASE=spring_app_db \
     -e MYSQL_USER=app_user \
     -e MYSQL_PASSWORD=test123 \
        mysql:latest

sleep 10s

sudo docker ps

echo "Creating app image"

sudo docker build -f Dockerfile -t spring-jpa-app .

sleep 10s

sudo docker run --name spring-jpa-app-container --link mysql-docker-container -p 8087:8080 -t app