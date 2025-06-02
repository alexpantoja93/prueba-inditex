package com.alexpantoja.prueba_inditex.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PriceE2ETest {
  @Autowired private TestRestTemplate restTemplate;

  @Test
  void shouldReturnExpectedPriceResponse() {
    String url = "/api/prices?product_id=35455&brand_id=1&application_date=2020-06-14T10:00:00";

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(response.getBody()).contains("\"productId\":35455");
    assertThat(response.getBody()).contains("\"brandId\":1");
    assertThat(response.getBody()).contains("\"price\":35.5");
  }
}
