package com.example.Todo.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Todo {

    private Long id;
    private String contents;
    private String name;
    private String password;
    private LocalDateTime createDate;
    private LocalDateTime editDate;

    public Todo(String contents, String name, String password, LocalDateTime createDate, LocalDateTime editDate) {
        this.contents = contents;
        this.name = name;
        this.password = password;
        this.createDate = createDate;
        this.editDate = editDate;
    }
}
