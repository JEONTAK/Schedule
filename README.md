# Schedule

---

## Lv 0. API 명세 및 ERD 작성

### Requirement

- [X] API 명세서 작성
- [X] ERD 작성
- [X] SQL 작성

#### Definition

- [X] API 명세서 작성
  - [X] 일정 API 설계
    - [X] 일정 생성(등록)
    - [X] 전체 일정 조회
    - [X] 특정 일정 조회
    - [X] 특정 일정 수정
    - [X] 특정 일정 삭제
- [X] ERD 작성
  - [X] schedule
    - [X] id : 일정 아이디 / BIGINT / (PK)
    - [X] name : 일정 작성자 / VARCHAR(30)
    - [X] password : 비밀번호 / VARCHAR(20)
    - [X] createDate : 작성일 / TIMESTAMP
    - [X] editDate : 수정일 / TIMESTAMP
- [X] SQL 작성

#### API 명세서

| 기능        | HTTP Method | URL                              | request                                                      | Response             | HTTP Status |
|-----------|-------------|----------------------------------|--------------------------------------------------------------|----------------------|-------------|
| 일정 생성(등록) | **POST**    | `/schedules`                     |request body를 통해 할일, 작성자명, 비밀번호를 담아 보내 일정 등록 요청.        | 등록 정보 JSON 데이터 반환    | `200 OK`    |
| 전체 일정 조회  | **GET**     | `/schedules/{name}and{editDate}` |request body 없이 모든 일정을 조회.                            | 조건에 해당하는 JSON 데이터 반환 | `200 OK`    |
| 특정 일정 조회  | **GET**     | `/schedules/{scheduleId}`        |query parameter로 ID 지정 후 선택 일정 조회.                    | 조건에 해당하는 JSON 데이터 반환 | `200 OK`    |
| 특정 일정 수정  | **PUT**     | `/schedules/{scheduleId}`        |query parameter로 ID를 지정하고 request body에 데이터를 담아 기존 데이터 수정. | 수정 정보 JSON 데이터 반환    | `200 OK`    |
| 특정 일정 삭제  | **DELETE**  | `/schedules/{scheduleId}`        |query parameter로 ID 지정 후 선택 일정 삭제.                     | 응답 코드 반환             | `200 OK`    |


#### ERD 작성
![img.png](ERD/ERD_Lv0.png)

#### SQL 작성
```mysql
CREATE TABLE schedule
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    name VARCHAR(30) COMMENT '작성자',
    password VARCHAR(20) COMMENT '비밀 번호',
    createDate TIMESTAMP COMMENT '작성일',
    editDate TIMESTAMP(20) COMMENT '수정일'
);
```
---

## Commit Convention

### 형식

```
type(scope) : short summary

[body]

[footer]
```

<br>type : 커밋의 목적
<br>scope : 변경된 코드의 범위
<br>short summary : 커밋의 간략한 설명
<br>body : 커밋의 자세한 설명
<br>footer : 부가 정보

### type

<br>feat: 새로운 기능 추가.
<br>fix: 버그 수정.
<br>docs: 문서 수정 (README.md, 주석 등).
<br>style: 코드 포맷팅, 세미콜론 누락 등 기능에 영향을 미치지 않는 변경.
<br>refactor: 코드 리팩토링 (기능 변화 없음).
<br>test: 테스트 코드 추가/수정.
<br>chore: 빌드 프로세스 또는 패키지 매니저 설정 수정.
<br>perf: 성능 개선을 위한 변경.
<br>ci: CI 설정 변경.
<br>build: 빌드 관련 파일 변경.

#### Type Emoji

✨ : feat
<br>🐛 : fix
<br>📝 : docs
<br>🎨 : style
<br>🔨 : refactor
<br>🚀 : perf
<br>✅ : test
<br>📦 : chore
<br>🗑️ : delete
---

## Code Convention

<br>class name : PascalCase(ex: MyClass)
<br>method and var : camelCase(ex: userName)
<br>const : UPPER_SNAKE_CASE(ex: MAX_LENGTH)

class : 명사형
<br>interface : 형용사 or 명사형
<br>method : 동사형
<br>var : 명사형, 의미 알수 있도록 명확히
<br>들여 쓰기 : 4칸
<br>중괄호 : 한 줄 아래에서 시작
<br>공백 : 연산자, 피연산자 사이에 공백 사용 ex : total = price + tax; / calculateTotal(price, tax);

주석 : 가능하면 JavaDoc 형식 사용

코드 : 클래스 내부 코드는 const, field, constructor, method 순으로 작성

---