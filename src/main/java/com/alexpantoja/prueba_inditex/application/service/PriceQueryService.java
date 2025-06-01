package com.alexpantoja.prueba_inditex.application.service;

import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.domain.repository.PriceRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PriceQueryService {

  private final PriceRepository priceRepository;

  public Price getApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate) {
    return priceRepository
        .findApplicablePrice(productId, brandId, applicationDate)
        .orElseThrow(() -> new RuntimeException("No price found"));
  }
}
