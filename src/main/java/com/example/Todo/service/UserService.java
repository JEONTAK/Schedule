package com.example.Todo.service;

import com.example.Todo.dto.UserRequestDto;
import com.example.Todo.dto.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserResponseDto saveUser(UserRequestDto requestDto);

    UserResponseDto updateUser(Long id, String name, String email);

    void deleteUser(Long id);
}
