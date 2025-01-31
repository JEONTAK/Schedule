package com.example.Todo.dto;

import com.example.Todo.entity.Todo;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoResponseDto {

    private Long id;
    private String contents;
    private Long userId;
    private String userName;
    private String password;
    private LocalDateTime createDate;
    private LocalDateTime editDate;

    public TodoResponseDto(Long id, Todo todo) {
        this.id = id;
        this.contents = todo.getContents();
        this.userId = todo.getUserId();
        this.password = todo.getPassword();
        this.createDate = todo.getCreateDate();
        this.editDate = todo.getEditDate();
    }

    public TodoResponseDto(Todo todo, String userName) {
        this.id = todo.getId();
        this.contents = todo.getContents();
        this.userId = todo.getUserId();
        this.userName = userName;
        this.password = todo.getPassword();
        this.createDate = todo.getCreateDate();
        this.editDate = todo.getEditDate();
    }
}
