package com.example.Todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoRequestDto {

    @Size(max = 200)
    private String contents;

    private Long userId;

    private String userName;

    @NotNull
    @NotBlank
    private String password;

}
