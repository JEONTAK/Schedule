package com.example.Todo.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, "서버 에러입니다. 잠시 후 다시 시도해 주세요."),
    INVALID_PASSWORD(400, "할일 수정시 비밀번호가 일치해야 합니다."),
    TODO_DELETE_BAD_REQUEST(400, "존재하는 할일만 삭제 가능합니다."),
    TODO_FIND_BAD_REQUEST(400, "할일 조회시 존재하는 유저 아이디만 가능 합니다."),
    TODO_SAVE_BAD_REQUEST(400, "할일 등록시 내용은 200자 이하, 비밀번호가 존재해야 합니다."),
    TODO_UPDATE_DATA_BAD_REQUEST(400, "할일 수정시 내용, 유저 아이디가 존재해야 합니다."),
    TODO_UPDATE_ID_NOT_FOUND(400, "존재하는 할일만 수정 가능합니다."),
    USER_DELETE_ID_NOT_FOUND(400, "존재하는 유저만 삭제 가능합니다."),
    USER_SAVE_BAD_REQUEST(400, "유저 등록시 형식에 맞는 이메일이 존재해야 합니다."),
    USER_UPDATE_DATA_BAD_REQUEST(400, "유저 정보 수정시 이름, 이메일이 존재해야 합니다."),
    USER_UPDATE_ID_NOT_FOUND(400, "존재하는 유저만 수정 가능합니다.");

    private final int status;
    private final String message;
}
