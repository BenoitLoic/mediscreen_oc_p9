package com.mediscreen.patientassessment.config;

import com.mediscreen.patientassessment.feign.exception.RetrieveMessageErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Logger;
import feign.codec.ErrorDecoder;

/** Configuration for Feign client. */
@Configuration
public class ClientConfig {

  @Bean
  public Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }

  @Bean
  public ErrorDecoder errorDecoder() {
    return new RetrieveMessageErrorDecoder();
  }
}
