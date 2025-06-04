package com.alexpantoja.prueba_inditex.domain.exception;

import java.time.LocalDateTime;

public class PriceNotFoundException extends RuntimeException {
  public PriceNotFoundException(Long productId, Long brandId, LocalDateTime applicationDate) {
    super(
        String.format(
            "No applicable price found for productId=%d, brandId=%d at %s",
            productId, brandId, applicationDate));
  }
}
