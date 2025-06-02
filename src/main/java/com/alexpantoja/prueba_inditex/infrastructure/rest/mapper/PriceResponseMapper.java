package com.alexpantoja.prueba_inditex.infrastructure.rest.mapper;

import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.infrastructure.rest.dto.PriceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceResponseMapper {
  @Mapping(source = "productId.value", target = "productId")
  @Mapping(source = "brandId.value", target = "brandId")
  @Mapping(source = "money.amount", target = "price")
  @Mapping(source = "dateRange.start", target = "startDate")
  @Mapping(source = "dateRange.end", target = "endDate")
  @Mapping(source = "priceId.value", target = "rateCode")
  @Mapping(target = "applicationDate", ignore = true)
  PriceResponse toResponse(Price price);
}
