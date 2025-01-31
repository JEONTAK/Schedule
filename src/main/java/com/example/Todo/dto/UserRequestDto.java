package com.example.Todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequestDto {

    private String name;
    private String email;
    private String gender;

}
