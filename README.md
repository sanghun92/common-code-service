# 공통 코드 서비스

공통 코드 조회 API 입니다.

[API 문서](http://localhost:8080/docs.html)
(애플리케이션 구동 후 확인 가능)
---

## 개발 환경

- Java 11
- Spring boot 2.7.12
- Gradle 7.6.1
- MySQL 8

---

## 실행 방법
```shell
./gradlew clean build
```
```shell
java -jar ./build/libs/common-code-service-0.0.1.jar
```

---

## 요구사항 정의
- 공통 코드
  - [X] 공통 코드는 한글명과 코드값을 가진다.
  - [X] 각 공통 코드는 그룹에 포함된다.
  - [X] (그룹 코드값, 공통 코드 코드값)은 고유값이다.
  - [X] 공통 코드별 조회 API를 제공한다.
  - [X] (Option) 공통 코드 생성 API를 제공한다.


- 공통 코드 그룹
  - [X] 공통 코드 그룹은 한글명과 코드값을 가진다.
  - [X] 최소 하나 이상의 공통 코드를 포함한다.
  - [X] 그룹 코드값은 고유값이다.
  - [X] 공통 코드 그룹 조회 API를 제공한다.
    - 해당 그룹에 포함 된 공통 코드도 함께 제공한다.
  - [X] (Option) 공통 코드 그룹 생성 API를 제공한다.
