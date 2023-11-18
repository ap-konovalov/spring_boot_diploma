package ru.netology.moneytransferservice.advices;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.moneytransferservice.model.ErrorResponseDto;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> notValidArgumentsHandler(MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getBindingResult().getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return ResponseEntity.badRequest().body(getErrorResponseDto(errorMessages.toString(), 0));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> jsonParseErrorHandler(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(getErrorResponseDto(exception.getMessage(), 1));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> internalServerErrorHandler(Exception exception) {
        return ResponseEntity.internalServerError().body(getErrorResponseDto(exception.getMessage(), 2));
    }

    private static ErrorResponseDto getErrorResponseDto(String errorMessage, int errorId) {
        return ErrorResponseDto.builder()
                .message(errorMessage)
                .id(errorId)
                .build();
    }
}
