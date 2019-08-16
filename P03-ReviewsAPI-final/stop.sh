#!/bin/bash

sudo docker stop spring-jpa-app-container

echo "Stopping container spring-jpa-app-container"

sudo docker container rm spring-jpa-app-container

echo "Removing container spring-jpa-app-container"

sleep 1s

sudo docker stop mysql-docker-container

echo "Stopping container mysql-docker-container"

sudo docker container rm mysql-docker-container

echo "Removing container mysql-docker-container"