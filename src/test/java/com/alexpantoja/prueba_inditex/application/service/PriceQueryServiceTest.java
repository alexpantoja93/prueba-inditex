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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceQueryServiceTest {

  @Mock private PriceRepository priceRepository;

  @InjectMocks private PriceQueryService priceQueryService;

  private static final BrandId BRAND_ID = new BrandId(1L);
  private static final ProductId PRODUCT_ID = new ProductId(35455L);
  private static final LocalDateTime NOW = LocalDateTime.now();

  @Test
  @DisplayName("Should return the price when found")
  void shouldReturnPriceWhenFound() {
    var expected =
        new Price(
            new PriceId(1L),
            BRAND_ID,
            PRODUCT_ID,
            1,
            new Money(new BigDecimal("35.50"), "EUR"),
            new DateRange(NOW.minusDays(1), NOW.plusDays(1)));

    when(priceRepository.findApplicablePrice(BRAND_ID, PRODUCT_ID, NOW))
        .thenReturn(Optional.of(expected));

    var result = priceQueryService.getApplicablePrice(BRAND_ID, PRODUCT_ID, NOW);

    assertThat(result).isEqualTo(expected);
    verify(priceRepository).findApplicablePrice(BRAND_ID, PRODUCT_ID, NOW);
  }

  @Test
  @DisplayName("Should throw exception when price not found")
  void shouldThrowExceptionWhenPriceNotFound() {
    when(priceRepository.findApplicablePrice(BRAND_ID, PRODUCT_ID, NOW))
        .thenReturn(Optional.empty());

    assertThatThrownBy(() -> priceQueryService.getApplicablePrice(BRAND_ID, PRODUCT_ID, NOW))
        .isInstanceOf(PriceNotFoundException.class)
        .hasMessageContaining("No applicable price found");
    verify(priceRepository).findApplicablePrice(BRAND_ID, PRODUCT_ID, NOW);
  }
}
