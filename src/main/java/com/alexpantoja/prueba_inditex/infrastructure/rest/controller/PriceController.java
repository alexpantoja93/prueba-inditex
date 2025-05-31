package com.alexpantoja.prueba_inditex.infrastructure.rest.controller;

import com.alexpantoja.prueba_inditex.application.service.PriceQueryService;
import com.alexpantoja.prueba_inditex.domain.model.Price;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final PriceQueryService priceQueryService;

    public PriceController(PriceQueryService priceQueryService) {
        this.priceQueryService = priceQueryService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getPrice(
            @RequestParam("application_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            @RequestParam("product_id") Long productId,
            @RequestParam("brand_id") Long brandId
    ) {
        Price price = priceQueryService.getApplicablePrice(productId, brandId, applicationDate);

        return ResponseEntity.ok(Map.of(
                "product_id", price.getProductId(),
                "brand_id", price.getBrand().getBrandId(),
                "rate_code", price.getPriceList(),
                "application_date", applicationDate,
                "price", price.getPrice()
        ));
    }
}