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
    private LocalDateTime createDate;
    private LocalDateTime editDate;
    private Gender gender;

    public User(String name, String email, LocalDateTime createDate, LocalDateTime editDate, Gender gender) {
        this.name = name;
        this.email = email;
        this.createDate = createDate;
        this.editDate = editDate;
        this.gender = gender;
    }
}
