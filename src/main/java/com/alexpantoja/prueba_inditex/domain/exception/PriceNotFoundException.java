package com.alexpantoja.prueba_inditex.domain.exception;

import java.time.LocalDateTime;

public class PriceNotFoundException extends RuntimeException {
  public PriceNotFoundException(Long productId, Long brandId, LocalDateTime applicationDate) {
    super(
        "Price Not Found of="
            + productId
            + ", brandId="
            + brandId
            + ", fecha="
            + applicationDate);
  }
}
