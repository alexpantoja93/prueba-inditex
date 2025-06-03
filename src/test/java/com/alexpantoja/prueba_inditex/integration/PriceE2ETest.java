package com.alexpantoja.prueba_inditex.integration;

import static org.assertj.core.api.Assertions.assertThat;

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
  void shouldReturnExpectedPriceResponse() {
    String url = BASE_URL + "?product_id=35455&brand_id=1&application_date=2020-06-14T10:00:00";
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    String body = response.getBody();
    assertThat(body).contains("\"productId\":35455");
    assertThat(body).contains("\"brandId\":1");
    assertThat(body).contains("\"price\":35.5");
  }

  @Test
  void shouldReturn404WhenPriceNotFound() {
    String url = BASE_URL + "?product_id=99999&brand_id=1&application_date=2025-01-01T00:00:00";
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    String body = response.getBody();
    assertThat(body).contains("Price Not Found");
  }

  @Test
  void shouldReturn400ForInvalidParameters() {
    String url = BASE_URL + "?product_id=abc&brand_id=1&application_date=2025-01-01T00:00:00";
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void shouldReturnPriceWithHighestPriorityWhenConflictsExist() {
    String url = BASE_URL + "?product_id=35455&brand_id=1&application_date=2020-06-14T16:00:00";
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    String body = response.getBody();
    assertThat(body).contains("\"rateCode\":2");
    assertThat(body).contains("\"price\":25.45");
  }
}
