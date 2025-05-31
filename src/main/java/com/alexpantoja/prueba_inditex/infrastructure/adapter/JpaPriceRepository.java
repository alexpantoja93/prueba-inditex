package com.alexpantoja.prueba_inditex.infrastructure.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {

    Optional<PriceEntity> findTopByProductIdAndBrand_BrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(Long productId, Long brandId, LocalDateTime applicationDateStart, LocalDateTime applicationDateEnd);
}
