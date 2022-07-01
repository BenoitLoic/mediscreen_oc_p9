package com.mediscreen.patientassessment.feign.exception;

import com.mediscreen.patientassessment.exception.BadArgumentException;
import com.mediscreen.patientassessment.exception.DataNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import feign.Response;
import feign.codec.ErrorDecoder;


/**
 * Error Decoder for feign client error handling. Retrieve the error send by Feign client and map it
 * to a custom error 400 or 404.
 */
public class RetrieveMessageErrorDecoder implements ErrorDecoder {
  private ErrorDecoder errorDecoder = new Default();

  @Override
  public Exception decode(String methodKey, Response response) {
    ExceptionMessage message = null;
    try (InputStream bodyIs = response.body().asInputStream()) {
      ObjectMapper mapper = new ObjectMapper();
      message = mapper.readValue(bodyIs, ExceptionMessage.class);
    } catch (IOException e) {
      return new Exception(e.getMessage());
    }
    switch (response.status()) {
      case 400:
        return new BadArgumentException(
            message.getMessage() != null ? message.getMessage() : "Bad Request");
      case 404:
        return new DataNotFoundException(
            message.getMessage() != null ? message.getMessage() : "Not found");
      default:
        return errorDecoder.decode(methodKey, response);
    }
  }
}
