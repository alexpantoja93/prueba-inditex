package com.alexpantoja.prueba_inditex.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.alexpantoja.prueba_inditex.domain.exception.PriceNotFoundException;
import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.*;
import com.alexpantoja.prueba_inditex.domain.repository.PriceRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceQueryServiceTest {

  private PriceRepository priceRepository;
  private PriceQueryService priceQueryService;

  private static final BrandId BRAND_ID = new BrandId(1L);
  private static final ProductId PRODUCT_ID = new ProductId(35455L);
  private static final LocalDateTime NOW = LocalDateTime.of(2025, 6, 2, 10, 0);

  @BeforeEach
  void setUp() {
    priceRepository = mock(PriceRepository.class);
    priceQueryService = new PriceQueryService(priceRepository);
  }

  @Test
  @DisplayName("Returns the price when a valid price exists")
  void givenExistingPrice_whenQuerying_thenReturnsPrice() {
    Price expected =
        new Price(
            new PriceId(1L),
            BRAND_ID,
            PRODUCT_ID,
            1,
            new Money(new BigDecimal("35.50"), "EUR"),
            new DateRange(NOW.minusDays(1), NOW.plusDays(1)));

    when(priceRepository.findApplicablePrice(BRAND_ID, PRODUCT_ID, NOW))
        .thenReturn(Optional.of(expected));

    Price result = priceQueryService.getApplicablePrice(BRAND_ID, PRODUCT_ID, NOW);

    assertThat(result).isEqualTo(expected);
    verify(priceRepository).findApplicablePrice(BRAND_ID, PRODUCT_ID, NOW);
  }

  @Test
  @DisplayName("Throws PriceNotFoundException when no applicable price is found")
  void givenNoMatchingPrice_whenQuerying_thenThrowsPriceNotFoundException() {
    when(priceRepository.findApplicablePrice(BRAND_ID, PRODUCT_ID, NOW))
        .thenReturn(Optional.empty());

    assertThatThrownBy(() -> priceQueryService.getApplicablePrice(BRAND_ID, PRODUCT_ID, NOW))
        .isInstanceOf(PriceNotFoundException.class)
        .hasMessageContaining("productId");

    verify(priceRepository).findApplicablePrice(BRAND_ID, PRODUCT_ID, NOW);
  }
}