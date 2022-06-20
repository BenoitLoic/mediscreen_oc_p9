package com.mediscreen.patienthistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot application for Patient history microservice.
 * Contain the psv main method.
 */
@SpringBootApplication
public class PatienthistoryApplication {

  public static void main(String[] args) {
    SpringApplication.run(PatienthistoryApplication.class, args);
  }
}
