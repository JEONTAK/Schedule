CREATE TABLE schedule
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    name VARCHAR(30) COMMENT '작성자',
    password VARCHAR(20) COMMENT '비밀 번호',
    createDate TIMESTAMP COMMENT '작성일',
    editDate TIMESTAMP(20) COMMENT '수정일'
);