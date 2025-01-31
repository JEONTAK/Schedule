package com.example.Todo.dto;

import lombok.Getter;

@Getter
public class TodoRequestDto {

    private String contents;
    private Long userId;
    private String name;
    private String password;

}
