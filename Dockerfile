# 베이스 이미지 정의
FROM openjdk:11-jdk

# 작업 디렉토리 설정
WORKDIR /app

# .env 복사
#COPY $ENV_FILE_PATH .env

# JAR 파일 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# application-prod.properties 복사
COPY src/main/resources/application-prod.properties application-prod.properties

# 실행 시 외부 설정 파일도 읽도록 설정
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.additional-location=optional:file:./"]