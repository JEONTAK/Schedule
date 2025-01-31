package com.example.Todo.repository;

import com.example.Todo.dto.TodoResponseDto;
import com.example.Todo.entity.Todo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TodoRepository {

    TodoResponseDto saveTodo(Todo todo);

    List<TodoResponseDto> findTodos(Long userId, LocalDateTime editDate);

    List<TodoResponseDto> findTodos();

    List<TodoResponseDto> findTodoByUserId(Long userId);

    List<TodoResponseDto> findTodoByEditDate(LocalDateTime date);

    Optional<Todo> findTodoById(Long id);

    TodoResponseDto findTodoByIdOrElseThrow(Long id);

    int updateContents(Long id, String contents, LocalDateTime date);

    int deleteTodo(Long id);
}
