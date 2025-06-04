package com.alexpantoja.prueba_inditex.domain.model;

import com.alexpantoja.prueba_inditex.domain.model.valueobject.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class Price {

  @NonNull private final PriceId priceId;
  @NonNull private final BrandId brandId;
  @NonNull private final ProductId productId;
  private final int priority;
  @NonNull private final Money money;
  @NonNull private final DateRange dateRange;

  public Price(
      PriceId priceId,
      BrandId brandId,
      ProductId productId,
      int priority,
      Money money,
      DateRange dateRange) {
    if (priority < 0) {
      throw new IllegalArgumentException("Priority must be non-negative");
    }
    this.priceId = priceId;
    this.brandId = brandId;
    this.productId = productId;
    this.priority = priority;
    this.money = money;
    this.dateRange = dateRange;
  }

  @Builder(builderMethodName = "buildPrice")
  public static Price of(
      PriceId priceId,
      BrandId brandId,
      ProductId productId,
      int priority,
      Money money,
      DateRange dateRange) {
    return new Price(priceId, brandId, productId, priority, money, dateRange);
  }

  public boolean isApplicableAt(java.time.LocalDateTime dateTime) {
    return dateRange.includes(dateTime);
  }
}
