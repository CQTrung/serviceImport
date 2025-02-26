package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidException extends RuntimeException {

  public InvalidException(String message) {
    super(message);
  }

  public InvalidException(String message, Throwable cause) {
    super(message, cause);
  }
}
