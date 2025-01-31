package com.example.Todo.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {

    private Long id;
    private String name;
    private String email;
    private Gender gender;
    private LocalDateTime createDate;
    private LocalDateTime editDate;

    public User(String name, String email, Gender gender, LocalDateTime createDate, LocalDateTime editDate) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.createDate = createDate;
        this.editDate = editDate;
    }
}
