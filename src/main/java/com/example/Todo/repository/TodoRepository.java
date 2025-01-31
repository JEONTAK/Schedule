package com.example.Todo.repository;

import com.example.Todo.dto.TodoResponseDto;
import com.example.Todo.entity.Todo;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoRepository {

    TodoResponseDto saveTodo(Todo todo);

    Page<TodoResponseDto> findTodos(Pageable pageable);

    Page<TodoResponseDto> findTodos(Long userId, Pageable pageable);

    Optional<Todo> findTodoById(Long id);

    TodoResponseDto findTodoByIdOrElseThrow(Long id);

    int updateContents(Long id, String contents, LocalDateTime date);

    int deleteTodo(Long id);
}
