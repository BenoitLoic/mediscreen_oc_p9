package com.mediscreen.patientassessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PatientassessmentApplication {

  public static void main(String[] args) {
    SpringApplication.run(PatientassessmentApplication.class, args);
  }

}
