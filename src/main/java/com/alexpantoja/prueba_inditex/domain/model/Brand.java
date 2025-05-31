package com.alexpantoja.prueba_inditex.domain.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    private Long brandId;
    private String description;
}