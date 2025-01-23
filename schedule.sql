CREATE TABLE schedule
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    contents    TEXT COMMENT '할 일',
    name        VARCHAR(30) COMMENT '작성자',
    password    VARCHAR(20) COMMENT '비밀 번호',
    create_date TIMESTAMP COMMENT '작성일',
    edit_date   TIMESTAMP COMMENT '수정일'
);