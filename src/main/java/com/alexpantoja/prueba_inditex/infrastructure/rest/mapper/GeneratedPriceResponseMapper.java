package com.alexpantoja.prueba_inditex.infrastructure.rest.mapper;

import com.alexpantoja.generated.model.PriceResponse;
import com.alexpantoja.prueba_inditex.domain.model.Price;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GeneratedPriceResponseMapper {
  @Mapping(source = "price.brandId.value", target = "brandId")
  @Mapping(source = "price.productId.value", target = "productId")
  @Mapping(source = "price.priceId.value", target = "rateCode")
  @Mapping(source = "price.money.amount", target = "price")
  @Mapping(source = "applicationDate", target = "applicationDate")
  PriceResponse toResponse(Price price, LocalDateTime applicationDate);

  default OffsetDateTime map(LocalDateTime value) {
    return value != null ? value.atOffset(ZoneOffset.UTC) : null;
  }
}
