package com.alexpantoja.prueba_inditex.infrastructure.rest.controller;

import com.alexpantoja.prueba_inditex.application.service.PriceQueryService;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.BrandId;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.ProductId;
import com.alexpantoja.prueba_inditex.infrastructure.rest.dto.PriceResponse;
import com.alexpantoja.prueba_inditex.infrastructure.rest.mapper.PriceResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/prices")
public class PriceController {

  private final PriceQueryService priceQueryService;
  private final PriceResponseMapper priceResponseMapper;

  @Operation(
      summary = "Get applicable price",
      description =
          "Returns the applicable price based on product ID, brand ID, and application date")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Price found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PriceResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "No applicable price found",
            content = @Content)
      })
  @GetMapping
  public ResponseEntity<PriceResponse> getPrice(
      @Parameter(
              description = "Date and time of application (e.g. 2020-06-14T10:00:00)",
              required = true)
          @RequestParam("application_date")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime applicationDate,
      @Parameter(description = "ID of the product", required = true) @RequestParam("product_id")
          Long productId,
      @Parameter(description = "ID of the brand", required = true) @RequestParam("brand_id")
          Long brandId) {
    return priceQueryService
        .getApplicablePrice(new BrandId(brandId), new ProductId(productId), applicationDate)
        .map(priceResponseMapper::toResponse)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
