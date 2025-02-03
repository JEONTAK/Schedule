package com.example.Todo.controller;

import com.example.Todo.Exception.CustomException;
import com.example.Todo.Exception.ErrorCode;
import com.example.Todo.dto.UserRequestDto;
import com.example.Todo.dto.UserResponseDto;
import com.example.Todo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @Operation(description = "유저 등록")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto requestDto,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.USER_SAVE_BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.saveUser(requestDto), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    @Operation(description = "유저 수정")
    public ResponseEntity<UserResponseDto> updateTodo(@PathVariable Long id,
                                                      @Valid @RequestBody UserRequestDto requestDto,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.USER_SAVE_BAD_REQUEST);
        }
        return new ResponseEntity<>(
                userService.updateUser(id, requestDto.getName(), requestDto.getEmail()),
                HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    @Operation(description = "유저 삭제")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/users/init")
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
