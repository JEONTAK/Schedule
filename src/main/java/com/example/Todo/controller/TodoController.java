package com.example.Todo.controller;

import com.example.Todo.dto.TodoRequestDto;
import com.example.Todo.dto.TodoResponseDto;
import com.example.Todo.service.TodoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto requestDto) {
        return new ResponseEntity<>(todoService.saveTodo(requestDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> findTodos(@RequestParam(required = false) Long userId,
                                                           @RequestParam(required = false) String editDate) {
        return new ResponseEntity<>(todoService.findTodos(userId, editDate), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findTodoById(@PathVariable Long id) {
        return new ResponseEntity<>(todoService.findTodoById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long id,
                                                      @RequestBody TodoRequestDto requestDto) {
        return new ResponseEntity<>(
                todoService.updateTodo(id, requestDto.getContents(), requestDto.getUserId(), requestDto.getName(),
                        requestDto.getPassword()),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id,
                                           @RequestBody TodoRequestDto requestDto) {
        todoService.deleteTodo(id, requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
