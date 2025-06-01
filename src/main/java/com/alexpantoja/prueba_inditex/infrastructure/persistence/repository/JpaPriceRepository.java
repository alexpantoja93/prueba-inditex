package com.alexpantoja.prueba_inditex.infrastructure.persistence.repository;

import com.alexpantoja.prueba_inditex.infrastructure.persistence.entity.PriceEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {

  Optional<PriceEntity>
      findTopByProductIdAndBrand_BrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
          Long productId,
          Long brandId,
          LocalDateTime applicationDateStart,
          LocalDateTime applicationDateEnd);
}
