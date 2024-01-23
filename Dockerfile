# 베이스 이미지 정의
FROM openjdk:11-jdk

# 작업 디렉토리 설정
#WORKDIR /app

# JAR 파일 복사
ARG JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /projectmatch.jar

# 애플리케이션 실행 (스프링 부트 JAR 실행)
ENTRYPOINT ["java","-jar","/projectmatch.jar"]