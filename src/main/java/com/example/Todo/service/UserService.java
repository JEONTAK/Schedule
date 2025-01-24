package com.example.Todo.service;

import com.example.Todo.dto.UserRequestDto;
import com.example.Todo.dto.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserResponseDto saveUser(UserRequestDto requestDto);
}
