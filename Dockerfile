# Java 17이 설치된 가벼운 리눅스 환경에서 시작
FROM openjdk:17-jdk-slim

# JAR 파일(Java 실행 파일) 위치 확인하기
COPY build/libs/*.jar app.jar

# 컨테이너가 시작하면, 아래 명령을 자동으로 실행
# 컨테이너: 내가 만든 프로그램만 실행되는 완전 독립된 작은 컴퓨터
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 서버 포트 명시
EXPOSE 8080