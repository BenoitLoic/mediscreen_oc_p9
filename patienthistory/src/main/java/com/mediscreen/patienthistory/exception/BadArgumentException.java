package com.mediscreen.patienthistory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Custom runtime exception called when argument is illegal. Http Error code 400. */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadArgumentException extends RuntimeException {
  /**
   * Constructs a new runtime exception with the specified detail message. The cause is not
   * initialized, and may subsequently be initialized by a call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the
   *     {@link #getMessage()} method.
   */
  public BadArgumentException(String message) {
    super(message);
  }
}
