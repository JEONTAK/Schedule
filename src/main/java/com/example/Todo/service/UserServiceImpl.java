package com.example.Todo.service;

import com.example.Todo.dto.UserRequestDto;
import com.example.Todo.dto.UserResponseDto;
import com.example.Todo.entity.User;
import com.example.Todo.repository.UserRepository;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto requestDto) {
        LocalDateTime createDate = LocalDateTime.now();
        User user = new User(requestDto.getName(), requestDto.getEmail(), requestDto.getGender(),
                createDate, createDate);

        return userRepository.saveUser(user);
    }

    @Override
    public UserResponseDto updateUser(Long id, String name, String email) {
        if (name == null && email == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Id and name must be either of them.");
        }

        LocalDateTime date = LocalDateTime.now();

        int updatedRow = 0;

        if (name != null && email != null) {
            updatedRow = userRepository.updateUser(id, name, email, date);
        } else if (name != null) {
            updatedRow = userRepository.updateUserName(id, name, date);
        } else {
            updatedRow = userRepository.updateUserEmail(id, email, date);
        }

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return new UserResponseDto(userRepository.findUserById(id).get());
    }

    @Override
    public void deleteUser(Long id) {
        int deleteRow = userRepository.deleteUser(id);

        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}
