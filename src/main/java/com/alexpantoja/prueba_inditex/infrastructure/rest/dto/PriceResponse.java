package com.alexpantoja.prueba_inditex.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResponse(
        Long productId,
        Long brandId,
        Integer rateCode,
        LocalDateTime applicationDate,
        BigDecimal price
) {}