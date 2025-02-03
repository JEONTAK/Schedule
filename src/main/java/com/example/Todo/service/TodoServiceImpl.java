package com.example.Todo.service;

import com.example.Todo.Exception.CustomExceptionHandler;
import com.example.Todo.Exception.ErrorCode;
import com.example.Todo.dto.TodoRequestDto;
import com.example.Todo.dto.TodoResponseDto;
import com.example.Todo.entity.Todo;
import com.example.Todo.repository.TodoRepository;
import com.example.Todo.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoServiceImpl(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TodoResponseDto saveTodo(TodoRequestDto requestDto) {
        LocalDateTime createDate = LocalDateTime.now();
        Todo todo = new Todo(requestDto.getContents(), requestDto.getUserId(),
                requestDto.getPassword(),
                createDate, createDate);

        return todoRepository.saveTodo(todo);
    }


    @Override
    public Page<TodoResponseDto> findTodos(Long userId, Pageable pageable) {
        if (userId == null) {
            return todoRepository.findTodos(pageable);
        }

        if (isNotValidUserId(userId)) {
            throw new CustomExceptionHandler(ErrorCode.TODO_FIND_BAD_REQUEST);
        }

        return todoRepository.findTodos(userId, pageable);
    }

    private boolean isNotValidUserId(Long userId) {
        return todoRepository.findTodoById(userId).isEmpty();
    }

    @Override
    public TodoResponseDto findTodoById(Long id) {
        return todoRepository.findTodoByIdOrElseThrow(id);
    }

    @Override
    public TodoResponseDto updateTodo(Long id, String contents, Long userId, String name, String password) {
        if (contents == null && userId == null) {
            throw new CustomExceptionHandler(ErrorCode.TODO_UPDATE_DATA_BAD_REQUEST);
        }

        if (isNotMatchPassword(id, password)) {
            throw new CustomExceptionHandler(ErrorCode.INVALID_PASSWORD);
        }

        int updatedRow = 0;
        LocalDateTime date = LocalDateTime.now();

        if (contents != null && userId != null) {
            updatedRow = todoRepository.updateContents(id, contents, date);
            updatedRow += userRepository.updateUserName(userId, name, date);
        } else if (userId != null) {
            updatedRow = userRepository.updateUserName(userId, name, date);
        } else {
            updatedRow = todoRepository.updateContents(id, contents, date);
        }

        if (updatedRow == 0) {
            throw new CustomExceptionHandler(ErrorCode.TODO_UPDATE_ID_NOT_FOUND);
        }

        return new TodoResponseDto(todoRepository.findTodoById(id).get(), userRepository.findUserNameById(userId));
    }

    @Override
    public void deleteTodo(Long id, String password) {
        if (isNotMatchPassword(id, password)) {
            throw new CustomExceptionHandler(ErrorCode.INVALID_PASSWORD);
        }
        int deleteRow = todoRepository.deleteTodo(id);

        if (deleteRow == 0) {
            throw new CustomExceptionHandler(ErrorCode.TODO_UPDATE_ID_NOT_FOUND);
        }
    }

    private boolean isNotMatchPassword(Long id, String password) {
        TodoResponseDto todo = todoRepository.findTodoByIdOrElseThrow(id);
        return !Objects.equals(todo.getPassword(), password);
    }
}
