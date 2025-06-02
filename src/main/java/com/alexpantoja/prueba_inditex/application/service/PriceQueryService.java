package com.alexpantoja.prueba_inditex.application.service;

import com.alexpantoja.prueba_inditex.domain.exception.PriceNotFoundException;
import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.*;
import com.alexpantoja.prueba_inditex.domain.repository.PriceRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PriceQueryService {

  private final PriceRepository priceRepository;

  public Price getApplicablePrice(
      BrandId brandId, ProductId productId, LocalDateTime applicationDate) {

    log.info(
        "Buscando precio aplicable para brandId={}, productId={}, fecha={}",
        brandId,
        productId,
        applicationDate);

    return priceRepository
        .findApplicablePrice(brandId, productId, applicationDate)
        .orElseThrow(
            () -> new PriceNotFoundException(productId.value(), brandId.value(), applicationDate));
  }
}
