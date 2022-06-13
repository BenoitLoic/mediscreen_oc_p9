package com.mediscreen.patientinfo.controller.impl;

import com.mediscreen.patientinfo.controller.PatientController;
import com.mediscreen.patientinfo.exception.BadArgumentException;
import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientControllerImpl implements PatientController {

  Logger logger = LoggerFactory.getLogger(PatientControllerImpl.class);
  @Autowired private PatientService patientService;

  @GetMapping
  public String defaultPage() {
    return "voila la page d'acceuil";
  }

  /**
   * Get the patient with the given familyName (lastname) and givenName (firstname).
   *
   * @param familyName the patient family name
   * @param givenName the patient given name
   * @return the patient information
   */
  @Override
  @GetMapping(value = "/get")
  @ResponseStatus(HttpStatus.OK)
  public Patient getPatient(
      @RequestParam(name = "family",value = "") String familyName,
      @RequestParam(name = "given", value = "") String givenName) {
    if (familyName.isBlank()||givenName.isBlank()){
      logger.warn("Error, invalid parameters for familyName: "+familyName+" givenName: "+givenName+".");
      throw new BadArgumentException("KO, invalid argument.");
    }
    logger.trace("Get patient: " + familyName + " - " + givenName);
    return patientService.getPatient(familyName, givenName);
  }
}
