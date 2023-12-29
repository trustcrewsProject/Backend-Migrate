#!/bin/bash

PROJECT_ROOT_PATH="/home/ubuntu/apps"
JAR_FILE="$PROJECT_ROOT_PATH/springboot-webapp.jar"

APP_LOG="$PROJECT_ROOT_PATH/application.log"
ERROR_LOG="$PROJECT_ROOT_PATH/error.log"
DEPLOY_LOG="$PROJECT_ROOT_PATH/deploy.log"

TIME_NOW=$(date +%c)

# build 파일 복사
echo "[$TIME_NOW] $JAR_FILE 파일 복사" >> $DEPLOY_LOG
cp $PROJECT_ROOT_PATH/build/libs/*.jar $JAR_FILE

# jar 파일 실행
echo "[$TIME_NOW] $JAR_FILE 파일 실행" >> $DEPLOY_LOG
nohup java -jar $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_APP_PID=$(pgrep -f $JAR_FILE)
echo "[$TIME_NOW] > 실행된 프로세스 아이디: $CURRENT_APP_PID" >> $DEPLOY_LOG