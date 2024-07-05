# 베이스 이미지 정의
FROM openjdk:11-jdk

# 작업 디렉토리 설정
WORKDIR /app

# .env 복사
#COPY $ENV_FILE_PATH .env

# JAR 파일 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /app.jar

RUN ls -al /app.jar

# 애플리케이션 실행 (스프링 부트 JAR 실행)
ENTRYPOINT ["java","-jar","/app.jar"]