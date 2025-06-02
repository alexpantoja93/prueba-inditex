package com.alexpantoja.prueba_inditex.application.service;

import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.*;
import com.alexpantoja.prueba_inditex.domain.repository.PriceRepository;
import java.time.LocalDateTime;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PriceQueryService {

  private final PriceRepository priceRepository;

  public Optional<Price> getApplicablePrice(
      BrandId brandId, ProductId productId, LocalDateTime applicationDate) {
    return priceRepository.findApplicablePrice(brandId, productId, applicationDate);
  }
}
