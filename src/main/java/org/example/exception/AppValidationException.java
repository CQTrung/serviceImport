package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AppValidationException extends RuntimeException {

  public AppValidationException(String message) {
    super(message);
  }

  public AppValidationException(String message, Throwable cause) {
    super(message, cause);
  }
}
