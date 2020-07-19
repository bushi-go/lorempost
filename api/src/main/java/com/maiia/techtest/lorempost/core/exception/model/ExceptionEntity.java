package com.maiia.techtest.lorempost.core.exception.model;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExceptionEntity {
  private String businessCode;
  private String message;
  private final Instant timestamp = Instant.now();
}
