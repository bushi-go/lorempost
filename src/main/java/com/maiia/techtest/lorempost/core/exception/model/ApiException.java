package com.maiia.techtest.lorempost.core.exception.model;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

@Getter
public class ApiException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private ErrorCode code;

  public ApiException(ErrorCode code) {
    this(code, null);
  }

  public ApiException(ErrorCode code, @Nullable Throwable cause) {
    super(code.getMessage(), cause);
    this.code = code;
  }

  public ResponseEntity<ExceptionEntity> toResponseEntity() {
    return ResponseEntity.status(code.getStatus())
        .body(
            ExceptionEntity.builder()
                .businessCode(code.getBusinessCode())
                .message(code.getMessage())
                .build());
  }
}
