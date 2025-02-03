package com.example.Todo.controller;

import com.example.Todo.dto.TodoRequestDto;
import com.example.Todo.dto.TodoResponseDto;
import com.example.Todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/todos")
    @Operation(description = "할일 등록")
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto requestDto) {
        return new ResponseEntity<>(todoService.saveTodo(requestDto), HttpStatus.OK);
    }

    @GetMapping("/todos")
    @Operation(description = "페이징을 이용한 할일 조회, 추가적으로 유저 아이디 입력시 해당 유저의 할일 조회 가능")
    public ResponseEntity<Page<TodoResponseDto>> findTodos(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(required = false) Long userId) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(todoService.findTodos(userId, pageable), HttpStatus.OK);
    }

    @GetMapping("/todos/{id}")
    @Operation(description = "개별 할일 조회")
    public ResponseEntity<TodoResponseDto> findTodoById(@PathVariable Long id) {
        return new ResponseEntity<>(todoService.findTodoById(id), HttpStatus.OK);
    }

    @PutMapping("/todos/{id}")
    @Operation(description = "할일 수정")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long id,
                                                      @RequestBody TodoRequestDto requestDto) {
        return new ResponseEntity<>(
                todoService.updateTodo(id, requestDto.getContents(), requestDto.getUserId(), requestDto.getName(),
                        requestDto.getPassword()),
                HttpStatus.OK);
    }

    @DeleteMapping("/todos/{id}")
    @Operation(description = "할일 삭제")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id,
                                           @RequestBody TodoRequestDto requestDto) {
        todoService.deleteTodo(id, requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/todos/init")
    public void initialTodos() {
        for (int i = 0; i < 100; i++) {
            String content = "Hello" + (i + 1);
            Long userId = (long) (i % 10 + 1);
            String name = "User" + (userId);
            String password = "user" + (userId);
            TodoRequestDto requestDto = new TodoRequestDto(content, userId, name, password);
            todoService.saveTodo(requestDto);
        }
    }
}
