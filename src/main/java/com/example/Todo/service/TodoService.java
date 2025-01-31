package com.example.Todo.service;

import com.example.Todo.dto.TodoRequestDto;
import com.example.Todo.dto.TodoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoService {

    TodoResponseDto saveTodo(TodoRequestDto requestDto);

    Page<TodoResponseDto> findTodos(Long userId, Pageable pageable);

    TodoResponseDto findTodoById(Long id);

    TodoResponseDto updateTodo(Long id, String contents, Long userId, String name, String password);

    void deleteTodo(Long id, String password);
}
