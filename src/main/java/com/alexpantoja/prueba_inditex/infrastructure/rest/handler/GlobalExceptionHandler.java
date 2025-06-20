package com.alexpantoja.prueba_inditex.infrastructure.rest.handler;

import com.alexpantoja.prueba_inditex.domain.exception.PriceNotFoundException;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(PriceNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handlePriceNotFoundException(
      PriceNotFoundException ex) {
    log.warn("Price not found: {}", ex.getMessage());
    return buildResponse(HttpStatus.NOT_FOUND, "Price Not Found", ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationException(
      MethodArgumentNotValidException ex) {
    log.warn("Validation failed: {}", ex.getMessage());
    var errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(err -> err.getField() + ": " + err.getDefaultMessage())
            .toList();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            Map.of(
                "timestamp",
                LocalDateTime.now(),
                "status",
                HttpStatus.BAD_REQUEST.value(),
                "error",
                "Validation Error",
                "messages",
                errors));
  }

  @ExceptionHandler({
    MethodArgumentTypeMismatchException.class,
    MissingServletRequestParameterException.class
  })
  public ResponseEntity<Map<String, Object>> handleBadRequest(Exception ex) {
    log.warn("Bad request: {}", ex.getMessage());
    return buildResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
    log.error("Unexpected error: {}", ex.getMessage(), ex);

    if (ex instanceof PriceNotFoundException pne) {
      return handlePriceNotFoundException(pne);
    }

    return buildResponse(
        HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "An unexpected error occurred");
  }

  private ResponseEntity<Map<String, Object>> buildResponse(
      HttpStatus status, String error, String message) {
    return ResponseEntity.status(status)
        .body(
            Map.of(
                "timestamp",
                LocalDateTime.now(),
                "status",
                status.value(),
                "error",
                error,
                "message",
                message));
  }
}
