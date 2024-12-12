package ru.clevertec.exception.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.exception.CarNotFoundException;
import ru.clevertec.exception.CategoryNotFoundException;
import ru.clevertec.exception.ClientNotFoundException;
import ru.clevertec.exception.ReviewNotFoundException;
import ru.clevertec.exception.ShowroomNotFoundException;

import java.util.Date;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<AppError> handleCarNotFoundException(CarNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<AppError> handleClientNotFoundException(ClientNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<AppError> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ShowroomNotFoundException.class)
    public ResponseEntity<AppError> handleShowroomNotFoundException(ShowroomNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<AppError> handleReviewNotFoundException(ReviewNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    private ResponseEntity<AppError> buildErrorResponse(HttpStatus status, String message) {
        AppError error = new AppError(status.value(), message, new Date());
        return new ResponseEntity<>(error, status);
    }
}