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
        - [X] create_date : 작성일 / TIMESTAMP
        - [X] edit_date : 수정일 / TIMESTAMP
- [X] SQL 작성

#### API 명세서

|    기능     | HTTP Method |                      URL                       |                          request                           |       Response       | HTTP Status |
|:---------:|:-----------:|:----------------------------------------------:|:----------------------------------------------------------:|:--------------------:|:-----------:|
| 일정 생성(등록) |  **POST**   |                  `/schedules`                  |      request body를 통해 할일, 작성자명, 비밀번호를 담아 보내 일정 등록 요청.      |  등록 정보 JSON 데이터 반환   |  `200 OK`   |
| 전체 일정 조회  |   **GET**   | `/schedules?name={name}&edit_date={edit_date}` |           조건에 맞는 모든 일정을 조회.(조건 미존재 시, 전체 일정 조회)            | 조건에 해당하는 JSON 데이터 반환 |  `200 OK`   |
| 특정 일정 조회  |   **GET**   |           `/schedules/{schedule_id}`           |             query parameter로 ID 지정 후 선택 일정 조회.             | 조건에 해당하는 JSON 데이터 반환 |  `200 OK`   |
| 특정 일정 수정  |   **PUT**   |           `/schedules/{schedule_id}`           | query parameter로 ID를 지정하고 request body에 데이터를 담아 기존 데이터 수정. |  수정 정보 JSON 데이터 반환   |  `200 OK`   |
| 특정 일정 삭제  | **DELETE**  |           `/schedules/{schedule_id}`           |             query parameter로 ID 지정 후 선택 일정 삭제.             |       응답 코드 반환       |  `200 OK`   |

#### ERD 작성

![img.png](ERD/ERD_Lv0.png)

#### SQL 작성

```mysql
CREATE TABLE schedule
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    contents    TEXT COMMENT '할 일',
    name        VARCHAR(30) COMMENT '작성자',
    password    VARCHAR(20) COMMENT '비밀 번호',
    create_date TIMESTAMP COMMENT '작성일',
    edit_date   TIMESTAMP COMMENT '수정일'
);
```

---

## Lv 1. 일정 생성 및 조회

### Requirement

- 일정 생성
    - 할일, 작성자명, 비밀번호, 작성/수정일을 저장
    - 작성/수정일은 날짜와 시간을 모두 포함한 형태
    - 각 일정의 고유 식별자(ID)를 자동으로 생성하여 관리
    - 최초 입력시, 수정일과 작성일은 동일
- 전체 일정 조회
    - 다음 조건을 바탕으로 등록된 일정 목록을 전부 조회
        - 수정일(형식 : YYYY-MM-DD)
        - 작성자명
    - 조건 중 한 가지만 충족하거나, 둘다 충족을 하지 않을 수도, 두 가지를 모두 충족할 수도 있음.
    - 수정일 기준 내림차순으로 정렬하여 조회
- 선택 일정 조회
    - 선택한 일정 단건의 정보를 조회
    - ID를 사용하여 조회

#### Configuration

- [X] 일정 Entity
    - [X] 필드
        - id
        - 할일
        - 작성자명
        - 비밀번호
        - 작성/수정일

- [X] 일정 Controller
    - [X] 일정 생성 메서드
        - PostMapping 사용
        - RequestBody 로 데이터를 가져옴
        - 일정 Service 통해 일정 저장 후 return
    - [X] 전체 일정 조회 메서드
        - GetMapping 사용
        - RequestParam을 통해 수정일, 작성자명 데이터를 가져옴
        - 일정 Service 통해 조건에 맞는 일정을 List로 가져와 return
    - [X] 특정 일정 조회 메서드
        - GetMapping 사용
        - PathVariable통해 id 값 가져옴
        - 일정 Service 통해 id값에 맞는 일정 가져와 return

- [X] 일정 RequestDto
    - id, 작성일, 수정일 제외 데이터 사용
- [X] 일정 ResponseDto
    - 전체 데이터 사용

- [X] 일정 Service
    - [X] 일정 ServiceImpl
        - [X] 일정 저장 메서드
            - 일정 객체 생성
            - 생성한 객체를 사용해 일정 Repository에 저장 요청 후 반환 값return
        - [X] 전체 일정 조회 메서드
            - 들어온 조건을 사용해 일정 Repository에 일정 조회 요청 후 반환 값 return
        - [X] 특정 일정 조회 메서드
            - 들어온 id값을 사용해 일정 Repository에 일정 조회 요청 후 반환 값 return

- [X] 일정 Repository
    - [X] 일정 RepositoryImpl
        - [X] 일정 저장 메서드
            - JDBC 테이블 및 컬럼 설정
            - 파라미터를 들어온 값을 사용해 설정
            - JDBC에 저장한 이후 Key 값을 받음
            - Key값 과 들어온 데이터를 반환
        - [X] 전체 일정 조회 메서드
            - 특정 조건에 해당하는 데이터를 JDBC에서 query를 사용해 받아와 반환
        - [X] 특정 일정 조회 메서드
            - 특정 id에 해당하는 데이터를 JDBC에서 query를 사용해 받아와 반환

___

## Lv 2. 일정 수정 및 삭제

### Requirement

- 선택한 일정 수정
    - 선택한 일정 내용 중 할일, 작성자명 만 수정 가능
        - 서버에 일정 수정을 요청할 때 비밀번호를 함께 전달 (작성한 사용자가 맞는지 검증)
        - 작성일은 변경할 수 없고, 수정일은 수정 완료 시, 수정한 시점으로 변경

- 선택한 일정 삭제
    - 서버에 일정 삭제를 요청할 때 비밀번호를 함께 전달 (작성한 사용자가 맞는지 검증)

#### Configuration

- [ ] 일정 Controller
    - [ ] 일정 수정 메서드
        - PutMapping 사용
        - PathVariable 통해 id값 가져옴
        - RequestBody 로 데이터를 가져옴
        - 일정 Service 통해 id값에 맞는 일정 수정 후 return
    - [ ] 일정 삭제 메서드
        - DeleteMapping 사용
        - PathVariable 통해 id값 가져옴
        - RequestBody 로 데이터를 가져옴
        - 일정 Service 통해 id값에 맞는 일정 삭제 후 return

- [ ] 일정 Service
    - [ ] 일정 ServiceImpl
        - [ ] 일정 수정 메서드
            - 만약 할일, 작성자명이 비어있다면
                - 예외 처리 : BAD_REQUEST
            - 만약 id에 해당하는 일정이 존재하지 않는다면
                - 예외 처리 : NOT_FOUND
            - 만약 해당하는 일정의 비밀번호와 들어온 비밀번호가 다르다면
                - 예외 처리 : Unauthorized
            - 들어온 데이터를 사용해 일정 Repository에 일정 수정 요청 후 반환 값 return
        - [ ] 일정 삭제 메서드
            - 만약 id에 해당하는 일정이 존재하지 않는다면
                - 예외 처리 : NOT_FOUND
            - 만약 해당하는 일정의 비밀번호와 들어온 비밀번호가 다르다면
                - 예외 처리 : Unauthorized
            - 들어온 데이터를 사용해 일정 Repository에 일정 삭제 요청 후 반환 값 return


- [ ] 일정 Repository
    - [ ] 일정 RepositoryImpl
        - [ ] 일정 수정 메서드
            - JDBC 테이블 및 컬럼 설정
            - 파라미터를 들어온 값을 사용해 설정
            - JDBC에 저장한 이후 Key 값을 받음
            - Key값 과 들어온 데이터를 반환
        - [ ] 일정 삭제 메서드
            - 특정 조건에 해당하는 데이터를 JDBC에서 query를 사용해 받아와 반환

___

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