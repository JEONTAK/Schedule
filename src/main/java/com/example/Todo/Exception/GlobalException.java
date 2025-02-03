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

    /*@ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }*/

    @ExceptionHandler({Exception.class})
    protected ResponseEntity handleServerException(Exception ex) {
        System.out.println(ex.getClass().getName());

        return new ResponseEntity(new ErrorDto(INTERNAL_SERVER_ERROR.getStatus(), INTERNAL_SERVER_ERROR.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
