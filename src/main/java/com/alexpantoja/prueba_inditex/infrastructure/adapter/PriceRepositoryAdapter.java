package com.alexpantoja.prueba_inditex.infrastructure.adapter;

import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.domain.repository.PriceRepository;
import com.alexpantoja.prueba_inditex.infrastructure.persistence.mapper.PriceMapper;
import com.alexpantoja.prueba_inditex.infrastructure.persistence.repository.JpaPriceRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepository {

  private final JpaPriceRepository jpaPriceRepository;
  private final PriceMapper priceMapper;

  @Override
  public Optional<Price> findApplicablePrice(
      Long productId, Long brandId, LocalDateTime applicationDate) {
    return jpaPriceRepository
        .findTopByProductIdAndBrand_BrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            productId, brandId, applicationDate, applicationDate)
        .map(priceMapper::toDomain);
  }
}
