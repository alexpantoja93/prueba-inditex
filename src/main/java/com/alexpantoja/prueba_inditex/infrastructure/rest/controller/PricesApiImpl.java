package com.alexpantoja.prueba_inditex.infrastructure.rest.controller;

import com.alexpantoja.generated.api.ApiPrices;
import com.alexpantoja.generated.model.PriceResponse;
import com.alexpantoja.prueba_inditex.application.service.PriceQueryService;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.BrandId;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.ProductId;
import com.alexpantoja.prueba_inditex.infrastructure.rest.mapper.GeneratedPriceResponseMapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PricesApiImpl implements ApiPrices {

  private final PriceQueryService priceQueryService;
  private final GeneratedPriceResponseMapper mapper;

  @Override
  public ResponseEntity<PriceResponse> getPrice(
      OffsetDateTime applicationDate, Integer productId, Integer brandId) {

    log.info(
        "Request to /api/prices with productId={}, brandId={}, applicationDate={}",
        productId,
        brandId,
        applicationDate);

    LocalDateTime localDateTime = applicationDate.toLocalDateTime();

    var domainPrice =
        priceQueryService.getApplicablePrice(
            new BrandId(brandId.longValue()),
            new ProductId(productId.longValue()),
                localDateTime);

    var response = mapper.toResponse(domainPrice, localDateTime);
    return ResponseEntity.ok(response);
  }
}
