package com.example.Todo.dto;

import com.example.Todo.entity.Gender;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime editDate;
    private Gender gender;

}
