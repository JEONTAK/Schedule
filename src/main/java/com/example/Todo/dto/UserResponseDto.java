package com.example.Todo.dto;

import com.example.Todo.entity.Gender;
import com.example.Todo.entity.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private Gender gender;
    private LocalDateTime createDate;
    private LocalDateTime editDate;

    public UserResponseDto(long id, User user) {
        this.id = id;
        this.name = user.getName();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.createDate = user.getCreateDate();
        this.editDate = user.getEditDate();
    }

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.createDate = user.getCreateDate();
        this.editDate = user.getEditDate();
    }
}
