package com.mediscreen.patientinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot application for Patient information microservice.
 * Contain the psv main method.
 */
@SpringBootApplication
public class PatientinfoApplication {

  public static void main(String[] args) {
    SpringApplication.run(PatientinfoApplication.class, args);
  }
}
