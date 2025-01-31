package com.example.Todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoRequestDto {

    private String contents;
    private Long userId;
    private String name;
    private String password;

}
