package com.example.Todo.service;

import com.example.Todo.Exception.CustomException;
import com.example.Todo.Exception.ErrorCode;
import com.example.Todo.dto.UserRequestDto;
import com.example.Todo.dto.UserResponseDto;
import com.example.Todo.entity.User;
import com.example.Todo.repository.UserRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto requestDto) {
        if (isNullRequestDto(requestDto)) {
            throw new CustomException(ErrorCode.USER_SAVE_BAD_REQUEST);
        }

        LocalDateTime createDate = LocalDateTime.now();
        User user = new User(requestDto.getName(), requestDto.getEmail(), requestDto.getGender(),
                createDate, createDate);

        return userRepository.saveUser(user);
    }

    private boolean isNullRequestDto(UserRequestDto requestDto) {
        return requestDto.getName() == null || requestDto.getEmail() == null || requestDto.getGender() == null;
    }

    @Override
    public UserResponseDto updateUser(Long id, String name, String email) {
        if (name == null && email == null) {
            throw new CustomException(ErrorCode.USER_UPDATE_DATA_BAD_REQUEST);
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
            throw new CustomException(ErrorCode.USER_UPDATE_ID_NOT_FOUND);
        }

        return new UserResponseDto(userRepository.findUserById(id).get());
    }

    @Override
    public void deleteUser(Long id) {
        int deleteRow = userRepository.deleteUser(id);

        if (deleteRow == 0) {
            throw new CustomException(ErrorCode.USER_UPDATE_ID_NOT_FOUND);
        }
    }
}
