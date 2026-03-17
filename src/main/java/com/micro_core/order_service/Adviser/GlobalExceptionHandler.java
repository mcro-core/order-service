package com.micro_core.order_service.Adviser;

import com.micro_core.order_service.exceptions.InventoryStockValidationException;
import com.micro_core.order_service.exceptions.ResourceNotFoundException;
import com.micro_core.order_service.utill.ExceptionResponseDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(InventoryStockValidationException.class)
    public ResponseEntity<ExceptionResponseDto> handleInventoryStockValidationException(InventoryStockValidationException ex, WebRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    // Validation Errors (RequestBody)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return buildResponse(HttpStatus.BAD_REQUEST, "Validation Failed: " + errors.toString(), request);
    }

    // Validation Errors (PathVariable/RequestParam)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseDto> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String propertyPath = violation.getPropertyPath().toString();
            errors.put(propertyPath.substring(propertyPath.lastIndexOf('.') + 1), violation.getMessage());
        });

        return buildResponse(HttpStatus.BAD_REQUEST, "Constraint Violation: " + errors.toString(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> handleGlobalException(Exception ex, WebRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong! " + ex.getMessage(), request);
    }

    private ResponseEntity<ExceptionResponseDto> buildResponse(HttpStatus status, String message, WebRequest request) {
        ExceptionResponseDto response = ExceptionResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(response, status);
    }
}