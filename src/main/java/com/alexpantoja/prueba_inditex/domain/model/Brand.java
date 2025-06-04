package com.alexpantoja.prueba_inditex.domain.model;

import com.alexpantoja.prueba_inditex.domain.model.valueobject.BrandId;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
  private BrandId brandId;
  private String description;
}
