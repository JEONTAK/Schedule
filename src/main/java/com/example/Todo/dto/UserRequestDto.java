package com.example.Todo.dto;

import com.example.Todo.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequestDto {

    private String name;
    private String email;
    private Gender gender;

}
