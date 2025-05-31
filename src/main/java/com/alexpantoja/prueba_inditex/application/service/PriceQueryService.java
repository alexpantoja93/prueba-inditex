package com.alexpantoja.prueba_inditex.application.service;

import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.domain.repository.PriceRepository;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PriceQueryService {

    private final PriceRepository priceRepository;

    public PriceQueryService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price getApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate) {
        return priceRepository.findApplicablePrice(productId, brandId, applicationDate)
                .orElseThrow(() -> new RuntimeException("No price found"));
    }
}