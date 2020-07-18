package com.maiia.techtest.lorempost.core.exception.handlers;

import com.maiia.techtest.lorempost.core.exception.model.ApiException;
import com.maiia.techtest.lorempost.core.exception.model.ExceptionEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Log4j2
public class ApiExceptionHandler {

  @ExceptionHandler({ApiException.class})
  public ResponseEntity<ExceptionEntity> handle(ApiException ex, WebRequest request) {
    log.info("Error : " + ex.getMessage());
    return ex.toResponseEntity();
  }
}
