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

- With Docker (MySQL 8)
```shell
docker-compose -f ./docker/docker-compose.yml -p test up -d
java -jar ./build/libs/common-code-service-0.0.1.jar
```

- H2 (MySQL Mode)
```shell
java -jar ./build/libs/common-code-service-0.0.1.jar --spring.profiles.active=local 
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

---

## API 명세

### 공통 부분
#### Response
| Parameters | type   | descriptions |
|------------|--------|--------------|
| success    | String | 응답 성공 여부     |
| timestamp  | String | 응답 일자 및 시간   |
| body       | Object | 응답 데이터       |
| error      | Object | 에러           |

- error

| Parameters | type   | descriptions  |
|------------|--------|---------------|
| message    | String | 에러 메시지        |
| fields     | Array  | 유효하지 않은 필드 목록 |

- error.fields

| Parameters | type   | descriptions      |
|------------|--------|-------------------|
| field      | String | 유효하지 않은 필드명       |
| value      | String | 유효하지 않은 필드 요청값    |
| message    | String | 유효하지 않은 필드 에러 메시지 |

### 공통코드 그룹 등록
``
POST /api/code-group
``

#### Request Body
| Parameters  | Type   | Required | descriptions |
|-------------|--------|----------|--------------|
| name        | String | O        | 공통 코드 그룹명    |
| description | String | O        | 공통 코드 그룹 설명  |

```json
{
  "name": "BN",
  "description": "사업구분 코드"
}
```

#### Response
- 201 코드로 응답.

### 공통코드 그룹 조회
``
GET /api/code-group/{name}
``

#### Request Parameters
| Parameters | Type   | Required | descriptions |
|------------|--------|----------|--------------|
| name       | String | O        | 공통 코드 그룹명    |

#### Response
- body

| Parameters | type   | descriptions |
|------------|--------|--------------|
| name       | String | 공통 코드명       |
| codes      | Array  | 공통 코드 목록     |

- body.codes

| Parameters | type   | descriptions |
|------------|--------|--------------|
| name       | String | 공통 코드명       |
| label      | String | 공통 코드 라벨     |

```json
{
  "success": true,
  "timestamp": "2023-05-22T01:38:44.786296",
  "body": {
    "name": "ZP",
    "codes": [
      {
        "name": "ZP1",
        "label": "기본계획수립"
      },
      {
        "name": "ZP2",
        "label": "안전진단"
      },
      {
        "name": "ZP3",
        "label": "정비구역지정"
      },
      {
        "name": "ZP4",
        "label": "조합설립 추진위원회 승인"
      },
      {
        "name": "ZP5",
        "label": "조합설립인가"
      },
      {
        "name": "ZP6",
        "label": "사업시행인가"
      },
      {
        "name": "ZP7",
        "label": "관리처분인가"
      },
      {
        "name": "ZP8",
        "label": "착공신고"
      }
    ]
  },
  "error": {
    "message": "",
    "fields": []
  }
}
```

### 공통코드 등록
``
POST /api/code-group/{groupName}/codes
``

#### Request Parameters
| Parameters | Type   | Required | descriptions |
|------------|--------|----------|--------------|
| groupName  | String | O        | 공통 코드 그룹명    |

#### Request Body
| Parameters  | Type   | Required | descriptions |
|-------------|--------|----------|--------------|
| name        | String | O        | 공통 코드명       |
| label       | String | O        | 공통 코드 라벨     |
| description | String | O        | 공통 코드 설명     |

```json 
{
  "name": "ZP8",
  "label": "착공신고",
  "description": "진행단계 착공신고 코드"
}
```

#### Response
- 201 코드로 응답.

### 공통코드 조회
``
GET /api/codes/{name}
``

#### Request Parameters
| Parameters | Type   | Required | descriptions |
|------------|--------|----------|--------------|
| name       | String | O        | 공통 코드명       |

#### Response
- body

| Parameters | type   | descriptions |
|------------|--------|--------------|
| name       | String | 공통 코드명       |
| label      | String | 공통 코드 라벨     |

```json 
{
  "success": true,
  "timestamp": "2023-05-22T01:29:45.736989",
  "body": {
    "name": "ZP1",
    "label": "기본계획수립"
  },
  "error": {
    "message": "",
    "fields": []
  }
}
```
