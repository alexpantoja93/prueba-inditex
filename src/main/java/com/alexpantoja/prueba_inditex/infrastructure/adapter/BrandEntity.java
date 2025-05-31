package com.alexpantoja.prueba_inditex.infrastructure.adapter;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brands")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;

    @Column(length = 50, nullable = false)
    private String description;
}
