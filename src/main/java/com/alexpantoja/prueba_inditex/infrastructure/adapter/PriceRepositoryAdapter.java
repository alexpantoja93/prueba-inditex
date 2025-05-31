package com.alexpantoja.prueba_inditex.infrastructure.adapter;

import com.alexpantoja.prueba_inditex.domain.model.Brand;
import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.domain.repository.PriceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PriceRepositoryAdapter implements PriceRepository {

    private final JpaPriceRepository jpaPriceRepository;

    public PriceRepositoryAdapter(JpaPriceRepository jpaPriceRepository) {
        this.jpaPriceRepository = jpaPriceRepository;
    }

    @Override
    public Optional<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate) {
        return jpaPriceRepository
                .findTopByProductIdAndBrand_BrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        productId, brandId, applicationDate, applicationDate
                )
                .map(this::mapToDomain);
    }

    private Price mapToDomain(PriceEntity entity) {
        return Price.builder()
                .id(entity.getId())
                .brand(Brand.builder()
                        .brandId(entity.getBrand().getBrandId())
                        .description(entity.getBrand().getDescription())
                        .build())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .priceList(entity.getPriceList())
                .productId(entity.getProductId())
                .priority(entity.getPriority())
                .price(entity.getPrice())
                .curr(entity.getCurr())
                .build();
    }
}
