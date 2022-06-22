package com.mediscreen.patienthistory.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configuration for Bean creation. */
@Configuration
public class BeanConfig {

  /**
   * ObjectMapper Bean creation with JavaTime module.
   *
   * @return objectMapper
   */
  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper().registerModule(new JavaTimeModule());
  }
}
