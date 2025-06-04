package com.alexpantoja.prueba_inditex.infrastructure.persistence.mapper;

import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.DateRange;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.Money;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.PriceId;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.ProductId;
import com.alexpantoja.prueba_inditex.infrastructure.persistence.entity.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = BrandMapper.class)
public interface PriceMapper {

  @Mapping(target = "priceId", source = "id", qualifiedByName = "toPriceId")
  @Mapping(target = "brandId", source = "brand.brandId", qualifiedByName = "toBrandId")
  @Mapping(target = "productId", source = "productId", qualifiedByName = "toProductId")
  @Mapping(target = "priority", source = "priority")
  @Mapping(target = "money", source = ".", qualifiedByName = "toMoney")
  @Mapping(target = "dateRange", source = ".", qualifiedByName = "toDateRange")
  Price toDomain(PriceEntity priceEntity);

  @Named("toPriceId")
  static PriceId toPriceId(Long id) {
    return new PriceId(id);
  }

  @Named("toProductId")
  static ProductId toProductId(Long id) {
    return new ProductId(id);
  }

  @Named("toMoney")
  static Money toMoney(PriceEntity priceEntity) {
    return new Money(priceEntity.getPrice(), priceEntity.getCurr());
  }

  @Named("toDateRange")
  static DateRange toDateRange(PriceEntity priceEntity) {
    return new DateRange(priceEntity.getStartDate(), priceEntity.getEndDate());
  }
}
