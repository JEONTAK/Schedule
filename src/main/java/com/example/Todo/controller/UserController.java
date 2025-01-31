package com.example.Todo.controller;

import com.example.Todo.dto.UserRequestDto;
import com.example.Todo.dto.UserResponseDto;
import com.example.Todo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
        return new ResponseEntity<>(userService.saveUser(requestDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateTodo(@PathVariable Long id,
                                                      @RequestBody UserRequestDto requestDto) {
        return new ResponseEntity<>(
                userService.updateUser(id, requestDto.getName(), requestDto.getEmail()),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/init")
    public void initialUser() {
        for (int i = 0; i < 100; i++) {
            String name = "User" + (i + 1);
            String email = "user" + (i + 1) + "@example.com";
            if (i % 2 == 0) {
                UserRequestDto requestDto = new UserRequestDto(name, email, "M");
                userService.saveUser(requestDto);
            } else {
                UserRequestDto requestDto = new UserRequestDto(name, email, "F");
                userService.saveUser(requestDto);
            }
        }
    }
}
