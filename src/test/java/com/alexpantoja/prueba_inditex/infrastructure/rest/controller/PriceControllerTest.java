package com.alexpantoja.prueba_inditex.infrastructure.rest.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alexpantoja.prueba_inditex.application.service.PriceQueryService;
import com.alexpantoja.prueba_inditex.domain.exception.PriceNotFoundException;
import com.alexpantoja.prueba_inditex.domain.model.Price;
import com.alexpantoja.prueba_inditex.domain.model.valueobject.*;
import com.alexpantoja.prueba_inditex.infrastructure.rest.dto.PriceResponse;
import com.alexpantoja.prueba_inditex.infrastructure.rest.mapper.PriceResponseMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PriceController.class)
class PriceControllerTest {

  private static final String PRICES_ENDPOINT = "/api/prices";

  @Autowired private MockMvc mockMvc;

  @MockBean private PriceQueryService priceQueryService;

  @MockBean private PriceResponseMapper priceResponseMapper;

  @Test
  @DisplayName("Should return price for 2020-06-14T10:00")
  void givenValidParams_whenTimeIs20200614T1000_thenReturnRate1() throws Exception {
    simulateRequest(
        "2020-06-14T10:00:00", 1, 35455, 1, BigDecimal.valueOf(35.50), "2020-06-14T09:00:00");
  }

  @Test
  @DisplayName("Should return price for 2020-06-14T16:00")
  void givenValidParams_whenTimeIs20200614T1600_thenReturnRate2() throws Exception {
    simulateRequest(
        "2020-06-14T16:00:00", 1, 35455, 2, BigDecimal.valueOf(25.45), "2020-06-14T15:00:00");
  }

  @Test
  @DisplayName("Should return price for 2020-06-14T21:00")
  void givenValidParams_whenTimeIs20200614T2100_thenReturnRate1() throws Exception {
    simulateRequest(
        "2020-06-14T21:00:00", 1, 35455, 1, BigDecimal.valueOf(35.50), "2020-06-14T20:00:00");
  }

  @Test
  @DisplayName("Should return price for 2020-06-15T10:00")
  void givenValidParams_whenTimeIs20200615T1000_thenReturnRate3() throws Exception {
    simulateRequest(
        "2020-06-15T10:00:00", 1, 35455, 3, BigDecimal.valueOf(30.50), "2020-06-15T09:00:00");
  }

  @Test
  @DisplayName("Should return price for 2020-06-16T21:00")
  void givenValidParams_whenTimeIs20200616T2100_thenReturnRate4() throws Exception {
    simulateRequest(
        "2020-06-16T21:00:00", 1, 35455, 4, BigDecimal.valueOf(38.95), "2020-06-16T20:00:00");
  }

  @Test
  @DisplayName("Should return 404 when price not found")
  void givenNonExistentProduct_whenRequestingPrice_thenReturn404() throws Exception {
    var dateTimeStr = "2020-06-17T10:00:00";
    var brandId = 1;
    var productId = 99999L;
    var applicationDate = LocalDateTime.parse(dateTimeStr);

    when(priceQueryService.getApplicablePrice(
            new BrandId((long) brandId), new ProductId(productId), applicationDate))
        .thenThrow(new PriceNotFoundException(productId, (long) brandId, applicationDate));

    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("application_date", dateTimeStr)
                .param("product_id", String.valueOf(productId))
                .param("brand_id", String.valueOf(brandId)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404))
        .andExpect(jsonPath("$.error").value("Price Not Found"))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.timestamp").exists());
  }

  @Test
  @DisplayName("Should return 400 when invalid parameter type")
  void givenInvalidParameter_whenRequestingPrice_thenReturn400() throws Exception {
    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("application_date", "2020-06-14T10:00:00")
                .param("product_id", "abc")
                .param("brand_id", "1"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error").value("Bad Request"))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.timestamp").exists());
  }

  private void simulateRequest(
      String dateTimeStr,
      int brandId,
      long productId,
      int rateCode,
      BigDecimal price,
      String expectedStartDate)
      throws Exception {
    var applicationDate = LocalDateTime.parse(dateTimeStr);
    var startDate = LocalDateTime.parse(expectedStartDate);
    var endDate = applicationDate.plusHours(1);

    var mockPrice =
        Price.of(
            new PriceId(1L),
            new BrandId((long) brandId),
            new ProductId(productId),
            0,
            new Money(price, "EUR"),
            new DateRange(startDate, endDate));

    var mockResponse =
        new PriceResponse(productId, (long) brandId, rateCode, applicationDate, price);

    when(priceQueryService.getApplicablePrice(
            new BrandId((long) brandId), new ProductId(productId), applicationDate))
        .thenReturn(mockPrice);

    when(priceResponseMapper.toResponse(mockPrice, applicationDate)).thenReturn(mockResponse);

    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("application_date", dateTimeStr)
                .param("product_id", String.valueOf(productId))
                .param("brand_id", String.valueOf(brandId)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(productId))
        .andExpect(jsonPath("$.brandId").value(brandId))
        .andExpect(jsonPath("$.rateCode").value(rateCode))
        .andExpect(jsonPath("$.applicationDate").value(dateTimeStr));
  }

  @Test
  @DisplayName("Should return 400 Bad Request when product_id is invalid")
  void shouldReturnBadRequestWhenProductIdIsInvalid() throws Exception {
    mockMvc
        .perform(
            get(PRICES_ENDPOINT)
                .param("application_date", "2020-06-14T10:00:00")
                .param("product_id", "INVALID")
                .param("brand_id", "1"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error").value("Bad Request"));
  }
}
