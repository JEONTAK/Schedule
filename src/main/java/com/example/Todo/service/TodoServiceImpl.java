package com.example.Todo.service;

import com.example.Todo.dto.TodoRequestDto;
import com.example.Todo.dto.TodoResponseDto;
import com.example.Todo.entity.Todo;
import com.example.Todo.repository.TodoRepository;
import com.example.Todo.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public List<TodoResponseDto> findTodos(Long userId, String editDate) {
        if (userId == null && editDate == null) {
            return todoRepository.findTodos();
        }

        if (userId != null && editDate == null) {
            return todoRepository.findTodoByUserId(userId);
        }

        LocalDateTime date = LocalDateTime.parse(editDate);
        if (userId == null) {
            return todoRepository.findTodoByEditDate(date);
        }

        return todoRepository.findTodos(userId, date);
    }

    @Override
    public TodoResponseDto findTodoById(Long id) {
        return todoRepository.findTodoByIdOrElseThrow(id);
    }

    @Override
    public TodoResponseDto updateTodo(Long id, String contents, Long userId, String name, String password) {
        if (contents == null && userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and content must be either of them.");
        }

        if (isNotMatchPassword(id, password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Does not match password");
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return new TodoResponseDto(todoRepository.findTodoById(id).get());
    }

    @Override
    public void deleteTodo(Long id, String password) {
        if (isNotMatchPassword(id, password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Does not match password");
        }
        int deleteRow = todoRepository.deleteTodo(id);

        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }

    private boolean isNotMatchPassword(Long id, String password) {
        TodoResponseDto todo = todoRepository.findTodoByIdOrElseThrow(id);
        return !Objects.equals(todo.getPassword(), password);
    }
}
