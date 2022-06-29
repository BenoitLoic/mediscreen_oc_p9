package com.mediscreen.patientassessment.feign;

import com.mediscreen.patientassessment.model.PatientInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${patient-info-service.name}", url = "${patient-info-service.url}")
public interface PatientInfoClient {

  @RequestMapping(method = RequestMethod.GET, value = "/patient/id/get")
  PatientInfo getPatientByID(@RequestParam(value = "id") int patientId);

  @RequestMapping(method = RequestMethod.GET, value = "/patient/get")
  PatientInfo getPatientByFamilyNameAndGivenName(
      @RequestParam String family, @RequestParam String given);
}
