package com.example.Todo.repository;

import com.example.Todo.dto.UserResponseDto;
import com.example.Todo.entity.User;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    UserResponseDto saveUser(User user);

    Optional<User> findUserById(Long id);

    int updateUser(Long id, String name, String email, LocalDateTime date);

    int updateUserName(Long id, String name, LocalDateTime date);

    int updateUserEmail(Long id, String email, LocalDateTime date);

    int deleteUser(Long id);
}
