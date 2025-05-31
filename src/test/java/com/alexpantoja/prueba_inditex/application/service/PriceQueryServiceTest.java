package com.alexpantoja.prueba_inditex.application.service;

import com.alexpantoja.prueba_inditex.domain.model.Brand;
import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.domain.repository.PriceRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PriceQueryServiceTest {

  private final PriceRepository priceRepository = mock(PriceRepository.class);
  private final PriceQueryService priceQueryService = new PriceQueryService(priceRepository);

  @Test
  void shouldReturnPriceWhenAvailable() {

    Long productId = 35455L;
    Long brandId = 1L;
    LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

    Price mockPrice =
        Price.builder()
            .productId(productId)
            .brand(Brand.builder().brandId(brandId).description("ZARA").build())
            .priceList(1)
            .price(BigDecimal.valueOf(35.50))
            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
            .priority(0)
            .curr("EUR")
            .build();

    when(priceRepository.findApplicablePrice(productId, brandId, applicationDate))
        .thenReturn(Optional.of(mockPrice));

    Price result = priceQueryService.getApplicablePrice(productId, brandId, applicationDate);

    assertNotNull(result);
    assertEquals(BigDecimal.valueOf(35.50), result.getPrice());
    assertEquals(brandId, result.getBrand().getBrandId());
    verify(priceRepository).findApplicablePrice(productId, brandId, applicationDate);
  }

  @Test
  void shouldThrowExceptionWhenNoPriceFound() {

    Long productId = 99999L;
    Long brandId = 1L;
    LocalDateTime applicationDate = LocalDateTime.now();

    when(priceRepository.findApplicablePrice(productId, brandId, applicationDate))
        .thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              priceQueryService.getApplicablePrice(productId, brandId, applicationDate);
            });

    assertEquals("No price found", exception.getMessage());
  }
}
