package com.alexpantoja.prueba_inditex.domain.model.valueobject;

public record PriceId(Long value) {
  public PriceId {
    if (value == null || value <= 0) {
      throw new IllegalArgumentException("Price ID must be positive");
    }
  }
}
