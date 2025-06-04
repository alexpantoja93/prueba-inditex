package com.alexpantoja.prueba_inditex.infrastructure.persistence.mapper;

import com.alexpantoja.prueba_inditex.domain.model.Brand;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.BrandId;
import com.alexpantoja.prueba_inditex.infrastructure.persistence.entity.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BrandMapper {

  @Mapping(target = "brandId", source = "brandId", qualifiedByName = "toBrandId")
  Brand toDomain(BrandEntity entity);

  @Mapping(target = "brandId", source = "brandId", qualifiedByName = "fromBrandId")
  BrandEntity toEntity(Brand brand);

  @Named("toBrandId")
  default BrandId toBrandId(Long id) {
    return id != null ? new BrandId(id) : null;
  }

  @Named("fromBrandId")
  default Long fromBrandId(BrandId brandId) {
    return brandId != null ? brandId.value() : null;
  }
}
