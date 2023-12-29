#!/bin/bash

PROJECT_ROOT_PATH="/home/ubuntu/apps"
JAR_FILE="$PROJECT_ROOT_PATH/springboot-webapp.jar"

DEPLOY_LOG="$PROJECT_ROOT_PATH/deploy.log"

TIME_NOW=$(date +%c)

# 현재 구동중인 애플리케이션 PID
CURRENT_APP_PID=$(pgrep -f $JAR_FILE)

if [ -z "$CURRENT_APP_PID" ]; then
  echo "[$TIME_NOW] 현재 실행중인 애플리케이션이 없습니다." >> $DEPLOY_LOG
else
  echo "[$TIME_NOW] $CURRENT_APP_PID 애플리케이션 종료 " >> $DEPLOY_LOG
  kill -15 $CURRENT_APP_PID
fi