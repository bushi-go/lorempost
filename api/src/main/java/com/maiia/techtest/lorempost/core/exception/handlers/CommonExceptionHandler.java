package com.maiia.techtest.lorempost.core.exception.handlers;

import com.maiia.techtest.lorempost.core.exception.model.ExceptionEntity;
import java.util.NoSuchElementException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
@Log4j2
public class CommonExceptionHandler {
  @ExceptionHandler({Exception.class})
  public ResponseEntity<ExceptionEntity> handle(Exception ex, WebRequest request) {
    log.info("Common Error : " + ex.getMessage());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ExceptionEntity.builder().message(ex.getMessage()).build());
  }

  @ExceptionHandler({NoSuchElementException.class})
  public ResponseEntity<ExceptionEntity> handle(NoSuchElementException ex, WebRequest request) {
    log.info("Common Error : " + ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ExceptionEntity.builder().message(ex.getMessage()).build());
  }
}
