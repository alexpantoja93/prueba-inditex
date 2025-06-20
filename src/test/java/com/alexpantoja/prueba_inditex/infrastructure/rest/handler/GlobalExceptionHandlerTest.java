package com.alexpantoja.prueba_inditex.infrastructure.rest.handler;

import static org.assertj.core.api.Assertions.assertThat;

import com.alexpantoja.prueba_inditex.domain.exception.PriceNotFoundException;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

class GlobalExceptionHandlerTest {
  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

  @Test
  @DisplayName("Should handle PriceNotFoundException with 404")
  void testHandlePriceNotFoundException() {
    var ex = new PriceNotFoundException(123L, 1L, LocalDateTime.now());
    var response = handler.handlePriceNotFoundException(ex);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isInstanceOf(Map.class);
    assertThat(response.getBody()).containsEntry("error", "Price Not Found");
  }

  @Test
  @DisplayName("Should handle MethodArgumentTypeMismatchException with 400")
  void testHandleBadRequestWithTypeMismatch() {
    var ex =
        new MethodArgumentTypeMismatchException(
            "abc", Integer.class, "product_id", null, new IllegalArgumentException());
    var response = handler.handleBadRequest(ex);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).containsEntry("error", "Bad Request");
  }

  @Test
  @DisplayName("Should handle MissingServletRequestParameterException with 400")
  void testHandleBadRequestWithMissingParam() {
    var ex = new MissingServletRequestParameterException("product_id", "String");
    var response = handler.handleBadRequest(ex);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).containsEntry("error", "Bad Request");
  }

  @Test
  @DisplayName("Should handle unknown exceptions with 500")
  void testHandleGeneralException() {
    var ex = new RuntimeException("Something failed");
    var response = handler.handleGeneralException(ex);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).containsEntry("error", "Internal Server Error");
  }

  @Test
  @DisplayName("Should fallback to specific handling when exception is instanceof PriceNotFound")
  void testHandleGeneralExceptionDelegatesToSpecificHandler() {
    var ex = new PriceNotFoundException(999L, 1L, LocalDateTime.now());
    var response = handler.handleGeneralException(ex);

    assertThat(response.getBody()).containsEntry("error", "Price Not Found");
  }
}
