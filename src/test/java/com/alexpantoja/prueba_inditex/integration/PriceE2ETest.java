package com.alexpantoja.prueba_inditex.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PriceE2ETest {

  private static final String BASE_URL = "/api/prices";

  @Autowired private TestRestTemplate restTemplate;

  @Test
  @DisplayName("Should return expected price response for valid inputs")
  void shouldReturnExpectedPriceResponse() {
    var url = BASE_URL + "?product_id=35455&brand_id=1&application_date=2020-06-14T10:00:00";
    var response = restTemplate.getForEntity(url, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody())
        .contains("\"productId\":35455")
        .contains("\"brandId\":1")
        .contains("\"price\":35.5");
  }

  @Test
  @DisplayName("Should return 404 Not Found when no applicable price exists")
  void shouldReturn404WhenPriceNotFound() {
    var url = BASE_URL + "?product_id=99999&brand_id=1&application_date=2025-01-01T00:00:00";
    var response = restTemplate.getForEntity(url, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).contains("Price Not Found").contains("\"status\":404");
  }

  @Test
  @DisplayName("Should return 400 Bad Request for invalid product_id parameter")
  void shouldReturn400ForInvalidParameters() {
    var url = BASE_URL + "?product_id=abc&brand_id=1&application_date=2025-01-01T00:00:00";
    var response = restTemplate.getForEntity(url, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).contains("\"status\":400").contains("Bad Request");
  }

  @Test
  @DisplayName("Should return price with highest priority when multiple matches exist")
  void shouldReturnPriceWithHighestPriorityWhenConflictsExist() {
    var url = BASE_URL + "?product_id=35455&brand_id=1&application_date=2020-06-14T16:00:00";
    var response = restTemplate.getForEntity(url, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).contains("\"rateCode\":2").contains("\"price\":25.45");
  }
}
