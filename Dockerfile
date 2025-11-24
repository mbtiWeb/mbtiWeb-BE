# 1) Gradle 빌드 스테이지
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app

# 소스 전체 복사
COPY . .

# 테스트는 제외하고 JAR 빌드
RUN gradle clean build -x test


# 2) 실제 실행 스테이지 (경량 이미지)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 빌드된 JAR을 실행 환경으로 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# Spring Boot 기본 포트
EXPOSE 8080

# 컨테이너 실행 시 Spring Boot 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
