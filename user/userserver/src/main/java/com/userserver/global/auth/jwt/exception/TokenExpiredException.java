package com.userserver.global.auth.jwt.exception;

import com.userserver.global.error.model.CustomException;
import org.springframework.http.HttpStatus;

public class TokenExpiredException extends CustomException {
  public TokenExpiredException() {
    super(HttpStatus.UNAUTHORIZED, "TOKEN_EXPIRED");
  }
}
