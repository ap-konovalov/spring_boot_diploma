package ru.netology.moneytransferservice.advices;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.moneytransferservice.entitites.ErrorResponseDto;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> notValidArgumentsHandler(MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getBindingResult().getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .message(errorMessages.toString())
                .id(0)
                .build();
        return ResponseEntity.badRequest().body(errorResponseDto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> jsonParseErrorHandler(HttpMessageNotReadableException exception) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .message(exception.getMessage())
                .id(1)
                .build();
        return ResponseEntity.badRequest().body(errorResponseDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> internalServerErrorHandler(Exception exception) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .message(exception.getMessage())
                .id(2)
                .build();
        return ResponseEntity.internalServerError().body(errorResponseDto);
    }
}
