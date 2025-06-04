package com.alexpantoja.prueba_inditex.domain.model.valueobject;

import java.time.LocalDateTime;

public record DateRange(LocalDateTime start, LocalDateTime end) {
  public DateRange {
    if (start == null || end == null || end.isBefore(start)) {
      throw new IllegalArgumentException("Invalid date range");
    }
  }

  public boolean includes(LocalDateTime dateTime) {
    return (dateTime.equals(start) || dateTime.isAfter(start))
        && (dateTime.equals(end) || dateTime.isBefore(end));
  }
}
