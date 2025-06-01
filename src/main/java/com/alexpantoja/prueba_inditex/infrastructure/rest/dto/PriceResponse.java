package com.alexpantoja.prueba_inditex.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PriceResponse {
  private Long productId;
  private Long brandId;
  private Integer rateCode;
  private LocalDateTime applicationDate;
  private BigDecimal price;
}
