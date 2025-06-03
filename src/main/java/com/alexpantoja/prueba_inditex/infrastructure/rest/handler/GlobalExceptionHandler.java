package com.alexpantoja.prueba_inditex.infrastructure.rest.handler;

import java.time.LocalDateTime;
import java.util.Map;

import com.alexpantoja.prueba_inditex.domain.exception.PriceNotFoundException;
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
  public ResponseEntity<?> handlePriceNotFoundException(PriceNotFoundException ex) {
    log.warn("Price not found: {}", ex.getMessage());
    return buildResponse(HttpStatus.NOT_FOUND, "Price Not Found", ex.getMessage());
  }

  @ExceptionHandler({
    MethodArgumentTypeMismatchException.class,
    MissingServletRequestParameterException.class,
    MethodArgumentNotValidException.class
  })
  public ResponseEntity<?> handleBadRequest(Exception ex) {
    log.warn("Bad request: {}", ex.getMessage());
    return buildResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleGeneralException(Exception ex) {
    log.error("Unexpected error: {}", ex.getMessage(), ex);
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
