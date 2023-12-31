package ru.netology.moneytransferservice.advices;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.moneytransferservice.exceptions.NoSuchTransactionException;
import ru.netology.moneytransferservice.model.ErrorResponseDto;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> notValidArgumentsHandler(MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getBindingResult().getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .toList();
        return ResponseEntity.badRequest().body(getErrorResponseDto(errorMessages.toString(), 0));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> jsonParseErrorHandler(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(getErrorResponseDto(exception.getMessage(), 1));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> internalServerErrorHandler(Exception exception) {
        return ResponseEntity.internalServerError().body(getErrorResponseDto("Internal Server Error", 2));
    }

    @ExceptionHandler(NoSuchTransactionException.class)
    public ResponseEntity<ErrorResponseDto> noSuchTransactionErrorHandler(NoSuchTransactionException exception) {
        return ResponseEntity.badRequest().body(getErrorResponseDto(exception.getMessage(), 3));
    }

    private ErrorResponseDto getErrorResponseDto(String errorMessage, int errorId) {
        return ErrorResponseDto.builder()
                .message(errorMessage)
                .id(errorId)
                .build();
    }
}
