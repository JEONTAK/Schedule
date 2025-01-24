CREATE TABLE user
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '작성자 식별자',
    name        VARCHAR(30) COMMENT '작성자',
    email       VARCHAR(40) COMMENT '비밀 번호',
    create_date TIMESTAMP COMMENT '등록일',
    edit_date   TIMESTAMP COMMENT '수정일',
    gender      CHAR(1) COMMENT '성별'
);

CREATE TABLE todo
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    contents    TEXT COMMENT '할 일',
    user_id   BIGINT COMMENT '작성자 식별자',
    password    VARCHAR(20) COMMENT '비밀 번호',
    create_date TIMESTAMP COMMENT '작성일',
    edit_date   TIMESTAMP COMMENT '수정일',
    FOREIGN KEY (user_id) REFERENCES user (id)
);