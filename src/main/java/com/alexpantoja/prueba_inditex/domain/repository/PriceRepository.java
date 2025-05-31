package com.alexpantoja.prueba_inditex.domain.repository;

import com.alexpantoja.prueba_inditex.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {
    Optional<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate);
}