package com.userserver.global.auth.jwt.exception;

import com.userserver.global.error.model.CustomException;
import com.userserver.global.error.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class TokenExpiredException extends CustomException {
  public TokenExpiredException() {
    super(ErrorCode.TOKEN_EXPIRED);
  }
}
