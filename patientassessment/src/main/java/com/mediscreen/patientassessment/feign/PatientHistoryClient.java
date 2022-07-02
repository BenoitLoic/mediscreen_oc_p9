package com.mediscreen.patientassessment.feign;

import com.mediscreen.patientassessment.config.ClientConfig;
import com.mediscreen.patientassessment.model.History;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client for Patient History service. Contain method to get the patient history from patient
 * history service.
 */
@FeignClient(
    value = "${patient-history-service.name}",
    url = "${patient-history-service.url}",
    configuration = ClientConfig.class)
public interface PatientHistoryClient {

  /**
   * Get all Notes for the given patient. Can throw BadArgumentException if argument is blank or
   * null.
   *
   * @param familyName the patient's lastname
   * @param givenName the patient's firstname
   * @return collection with all saved notes
   */
  @GetMapping("/patHistory/name/get")
  History getPatientHistoryByFamilyNameAndGivenName(
      @RequestParam String familyName, @RequestParam String givenName);

  /**
   * Get all Notes for the given patient. Can throw BadArgumentException if argument is blank or
   * null.
   *
   * @param patientId the patient id
   * @return collection with all saved notes
   */
  @GetMapping("/patHistory/id/get")
  History getPatientHistoryById(@RequestParam int patientId);
}
