package com.alexpantoja.prueba_inditex.domain.repository;

import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.BrandId;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.ProductId;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {
  Optional<Price> findApplicablePrice(
      BrandId brandId, ProductId productId, LocalDateTime applicationDate);
}
