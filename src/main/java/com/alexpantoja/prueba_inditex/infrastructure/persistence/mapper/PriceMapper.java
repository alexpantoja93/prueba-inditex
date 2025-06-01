package com.alexpantoja.prueba_inditex.infrastructure.persistence.mapper;

import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.infrastructure.persistence.entity.PriceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BrandMapper.class)
public interface PriceMapper {
  PriceEntity toEntity(Price price);

  Price toDomain(PriceEntity priceEntity);
}
