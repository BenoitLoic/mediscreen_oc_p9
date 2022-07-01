package com.mediscreen.patientassessment.feign;

import com.mediscreen.patientassessment.config.ClientConfig;
import com.mediscreen.patientassessment.model.PatientInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client for Patient Info service. CContain method to get the patient information from
 * patient info service.
 */
@FeignClient(
    value = "${patient-info-service.name}",
    url = "${patient-info-service.url}",
    configuration = ClientConfig.class)
public interface PatientInfoClient {

  /**
   * Get the patient with the given id.
   *
   * @param patientId the patient id
   * @return the patient information
   */
  @RequestMapping(method = RequestMethod.GET, value = "/patient/id/get")
  PatientInfo getPatientByID(@RequestParam(value = "id") int patientId);

  /**
   * Get the patient with the given familyName (lastname) and givenName (firstname).
   *
   * @param family the patient family name
   * @param given the patient given name
   * @return the patient information
   */
  @RequestMapping(method = RequestMethod.GET, value = "/patient/get")
  PatientInfo getPatientByFamilyNameAndGivenName(
      @RequestParam String family, @RequestParam String given);
}
