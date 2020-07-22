package com.maiia.techtest.lorempost.core.exception.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  INVALID_SORT_FIELD("common.invalid.sortField", "You can't sort this way", HttpStatus.BAD_REQUEST);

  private String businessCode;
  private String message;
  private HttpStatus status;

  ErrorCode(String businessCode, String message, HttpStatus status) {
    this.businessCode = businessCode;
    this.message = message;
    this.status = status;
  }
}
