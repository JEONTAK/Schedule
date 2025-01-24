package com.example.Todo.repository;

import com.example.Todo.dto.UserResponseDto;
import com.example.Todo.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    UserResponseDto saveUser(User user);
}
