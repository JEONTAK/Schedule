package com.example.Todo.service;

import com.example.Todo.dto.TodoRequestDto;
import com.example.Todo.dto.TodoResponseDto;
import java.util.List;

public interface TodoService {

    TodoResponseDto saveTodo(TodoRequestDto requestDto);

    List<TodoResponseDto> findTodos(String name, String editDate);

    TodoResponseDto findTodoById(Long id);

    TodoResponseDto updateTodo(Long id, String contents, String name, String password);

    void deleteTodo(Long id, String password);
}
