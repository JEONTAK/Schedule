package com.example.Todo.Exception;

import static com.example.Todo.Exception.ErrorCode.INTERNAL_SERVER_ERROR;

import com.example.Todo.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler({CustomException.class})
    protected ResponseEntity handleCustomException(CustomException exception) {
        return new ResponseEntity(
                new ErrorDto(exception.getErrorCode().getStatus(), exception.getErrorCode().getMessage()),
                HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    @ExceptionHandler({ Exception.class })
    protected ResponseEntity handleServerException(Exception ex) {
        return new ResponseEntity(new ErrorDto(INTERNAL_SERVER_ERROR.getStatus(), INTERNAL_SERVER_ERROR.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
