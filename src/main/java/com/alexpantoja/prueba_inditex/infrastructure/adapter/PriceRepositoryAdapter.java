package com.alexpantoja.prueba_inditex.infrastructure.adapter;

import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.BrandId;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.ProductId;
import com.alexpantoja.prueba_inditex.domain.repository.PriceRepository;
import com.alexpantoja.prueba_inditex.infrastructure.persistence.mapper.PriceMapper;
import com.alexpantoja.prueba_inditex.infrastructure.persistence.repository.JpaPriceRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepository {

  private final JpaPriceRepository jpaPriceRepository;
  private final PriceMapper priceMapper;

  @Override
  public Optional<Price> findApplicablePrice(
      BrandId brandId, ProductId productId, LocalDateTime applicationDate) {
    log.debug("Llamando a PriceJpaRepository con parámetros: ...");
    return jpaPriceRepository
        .findTopByProductIdAndBrand_BrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            productId.value(), brandId.value(), applicationDate, applicationDate)
        .map(priceMapper::toDomain);
  }
}
