package com.alexpantoja.prueba_inditex.infrastructure.rest.mapper;

import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.infrastructure.rest.dto.PriceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceResponseMapper {
  @Mapping(source = "productId", target = "productId")
  @Mapping(source = "brand.brandId", target = "brandId")
  @Mapping(source = "priceList", target = "rateCode")
  @Mapping(source = "price", target = "price")
  PriceResponse toResponse(Price price);
}
