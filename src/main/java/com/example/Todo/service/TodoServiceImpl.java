package com.example.Todo.service;

import com.example.Todo.dto.TodoRequestDto;
import com.example.Todo.dto.TodoResponseDto;
import com.example.Todo.entity.Todo;
import com.example.Todo.repository.TodoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoResponseDto saveTodo(TodoRequestDto requestDto) {
        LocalDateTime createDate = LocalDateTime.now();
        Todo todo = new Todo(requestDto.getContents(), requestDto.getName(), requestDto.getPassword(),
                createDate, createDate);

        return todoRepository.saveTodo(todo);
    }

    @Override
    public List<TodoResponseDto> findTodos(String name, String editDate) {
        if (name == null && editDate == null) {
            return todoRepository.findTodos();
        }

        if (name != null && editDate == null) {
            return todoRepository.findByName(name);
        }

        LocalDateTime date = LocalDateTime.parse(editDate);
        if (name == null) {
            return todoRepository.findByEditDate(date);
        }

        return todoRepository.findTodos(name, date);
    }

    @Override
    public TodoResponseDto findTodoById(Long id) {
        return todoRepository.findTodoByIdOrElseThrow(id);
    }

    @Override
    public TodoResponseDto updateTodo(Long id, String contents, String name, String password) {
        if (contents == null && name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and content must be either of them.");
        }

        if (isNotMatchPassword(id, password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Does not match password");
        }

        int updatedRow = 0;
        LocalDateTime date = LocalDateTime.now();
        if (contents != null && name != null) {
            updatedRow = todoRepository.updateTodo(id, contents, name, date);
        } else if (name != null) {
            updatedRow = todoRepository.updateName(id, name, date);
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
        TodoResponseDto schedule = todoRepository.findTodoByIdOrElseThrow(id);
        return !Objects.equals(schedule.getPassword(), password);
    }
}
