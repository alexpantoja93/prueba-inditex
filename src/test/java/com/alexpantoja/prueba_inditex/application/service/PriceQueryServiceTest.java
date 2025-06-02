package com.alexpantoja.prueba_inditex.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.alexpantoja.prueba_inditex.domain.exception.PriceNotFoundException;
import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.*;
import com.alexpantoja.prueba_inditex.domain.repository.PriceRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PriceQueryServiceTest {

  private PriceRepository priceRepository;
  private PriceQueryService priceQueryService;

  @BeforeEach
  void setUp() {
    priceRepository = mock(PriceRepository.class);
    priceQueryService = new PriceQueryService(priceRepository);
  }

  @Test
  void shouldReturnPriceIfExists() {
    BrandId brandId = new BrandId(1L);
    ProductId productId = new ProductId(35455L);
    LocalDateTime applicationDate = LocalDateTime.now();
    Price expectedPrice =
        new Price(
            new PriceId(1L),
            brandId,
            productId,
            1,
            new Money(new BigDecimal("35.50"), "EUR"),
            new DateRange(applicationDate.minusDays(1), applicationDate.plusDays(1)));

    when(priceRepository.findApplicablePrice(brandId, productId, applicationDate))
        .thenReturn(Optional.of(expectedPrice));

    Price result = priceQueryService.getApplicablePrice(brandId, productId, applicationDate);

    assertEquals(expectedPrice, result);
  }

  @Test
  void shouldReturnEmptyIfNoPriceFound() {
    BrandId brandId = new BrandId(1L);
    ProductId productId = new ProductId(35455L);
    LocalDateTime applicationDate = LocalDateTime.now();

    when(priceRepository.findApplicablePrice(brandId, productId, applicationDate))
        .thenReturn(Optional.empty());

    assertThrows(
        PriceNotFoundException.class,
        () -> priceQueryService.getApplicablePrice(brandId, productId, applicationDate));
  }
}
