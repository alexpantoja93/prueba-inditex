package com.alexpantoja.prueba_inditex.infrastructure.rest.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alexpantoja.prueba_inditex.application.service.PriceQueryService;
import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.*;
import com.alexpantoja.prueba_inditex.infrastructure.rest.dto.PriceResponse;
import com.alexpantoja.prueba_inditex.infrastructure.rest.mapper.PriceResponseMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PriceController.class)
public class PriceControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private PriceQueryService priceQueryService;

  @MockBean private PriceResponseMapper priceResponseMapper;

  @Test
  void test1_shouldReturnPriceAt_2020_06_14_10_00() throws Exception {
    simulateRequest(
        "2020-06-14T10:00:00", 1, 35455, 1, BigDecimal.valueOf(35.50), "2020-06-14T09:00:00");
  }

  @Test
  void test2_shouldReturnPriceAt_2020_06_14_16_00() throws Exception {
    simulateRequest(
        "2020-06-14T16:00:00", 1, 35455, 2, BigDecimal.valueOf(25.45), "2020-06-14T15:00:00");
  }

  @Test
  void test3_shouldReturnPriceAt_2020_06_14_21_00() throws Exception {
    simulateRequest(
        "2020-06-14T21:00:00", 1, 35455, 1, BigDecimal.valueOf(35.50), "2020-06-14T20:00:00");
  }

  @Test
  void test4_shouldReturnPriceAt_2020_06_15_10_00() throws Exception {
    simulateRequest(
        "2020-06-15T10:00:00", 1, 35455, 3, BigDecimal.valueOf(30.50), "2020-06-15T09:00:00");
  }

  @Test
  void test5_shouldReturnPriceAt_2020_06_16_21_00() throws Exception {
    simulateRequest(
        "2020-06-16T21:00:00", 1, 35455, 4, BigDecimal.valueOf(38.95), "2020-06-16T20:00:00");
  }

  private void simulateRequest(
      String dateTimeStr,
      int brandIdRaw,
      long productIdRaw,
      int rateCode,
      BigDecimal price,
      String expectedStartDate)
      throws Exception {

    LocalDateTime applicationDate = LocalDateTime.parse(dateTimeStr);
    LocalDateTime startDate = LocalDateTime.parse(expectedStartDate);
    LocalDateTime endDate = applicationDate.plusHours(1);

    Price mockPrice =
        Price.of(
            new PriceId(1L),
            new BrandId((long) brandIdRaw),
            new ProductId(productIdRaw),
            0,
            new Money(price, "EUR"),
            new DateRange(startDate, endDate));

    PriceResponse mockResponse =
        new PriceResponse(
            productIdRaw, (long) brandIdRaw, rateCode, applicationDate, startDate, endDate, price);

    when(priceQueryService.getApplicablePrice(
            new BrandId((long) brandIdRaw), new ProductId(productIdRaw), applicationDate))
        .thenReturn(Optional.of(mockPrice));

    when(priceResponseMapper.toResponse(mockPrice)).thenReturn(mockResponse);

    mockMvc
        .perform(
            get("/api/prices")
                .param("application_date", dateTimeStr)
                .param("product_id", String.valueOf(productIdRaw))
                .param("brand_id", String.valueOf(brandIdRaw)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(productIdRaw))
        .andExpect(jsonPath("$.brandId").value(brandIdRaw))
        .andExpect(jsonPath("$.rateCode").value(rateCode))
        .andExpect(jsonPath("$.applicationDate").value(dateTimeStr))
        .andExpect(jsonPath("$.startDate").value(expectedStartDate));
  }
}
