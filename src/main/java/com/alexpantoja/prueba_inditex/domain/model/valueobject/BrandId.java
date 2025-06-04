package com.alexpantoja.prueba_inditex.domain.model.valueobject;

public record BrandId(Long value) {
  public BrandId {
    if (value == null || value <= 0) {
      throw new IllegalArgumentException("Brand ID must be positive");
    }
  }
}
