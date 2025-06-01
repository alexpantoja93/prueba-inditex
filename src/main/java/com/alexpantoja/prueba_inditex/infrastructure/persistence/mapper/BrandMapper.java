package com.alexpantoja.prueba_inditex.infrastructure.persistence.mapper;

import com.alexpantoja.prueba_inditex.domain.model.Brand;
import com.alexpantoja.prueba_inditex.infrastructure.persistence.entity.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
  Brand toDomain(BrandEntity entity);

  BrandEntity toEntity(Brand brand);
}
