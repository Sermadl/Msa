package com.userserver.global.auth.jwt.exception;

import com.userserver.global.error.model.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends CustomException {
  public InvalidTokenException() {
    super(HttpStatus.UNAUTHORIZED, "INVALID_TOKEN");
  }
}
