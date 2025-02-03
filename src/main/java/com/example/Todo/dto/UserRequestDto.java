package com.example.Todo.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequestDto {

    private String name;

    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    private String gender;
}
