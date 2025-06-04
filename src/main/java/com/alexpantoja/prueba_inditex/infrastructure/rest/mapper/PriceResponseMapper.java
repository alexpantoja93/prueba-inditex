package com.alexpantoja.prueba_inditex.infrastructure.rest.mapper;

import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.infrastructure.rest.dto.PriceResponse;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceResponseMapper {
  @Mapping(source = "price.productId.value", target = "productId")
  @Mapping(source = "price.brandId.value", target = "brandId")
  @Mapping(source = "price.money.amount", target = "price")
  @Mapping(source = "price.priceId.value", target = "rateCode")
  PriceResponse toResponse(Price price, LocalDateTime applicationDate);
}
