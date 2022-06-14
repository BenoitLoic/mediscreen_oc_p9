package com.mediscreen.patientinfo.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.mediscreen.patientinfo.controller.PatientController;
import com.mediscreen.patientinfo.exception.BadArgumentException;
import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.model.dto.UpdatePatientDto;
import com.mediscreen.patientinfo.service.PatientService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

@RestController
@RequestMapping("/patient")
public class PatientControllerImpl implements PatientController {

  Logger logger = LoggerFactory.getLogger(PatientControllerImpl.class);
  @Autowired private PatientService patientService;

  @GetMapping
  public String defaultPage() {
    return "home";
  }

  /**
   * Get the patient with the given familyName (lastname) and givenName (firstname).
   *
   * @param familyName the patient family name
   * @param givenName the patient given name
   * @return the patient information
   */
  @GetMapping(value = "/get")
  @ResponseStatus(HttpStatus.OK)
  public Patient getPatient(
      @RequestParam(name = "family") String familyName,
      @RequestParam(name = "given") String givenName) {
    if (familyName.isBlank() || givenName.isBlank()) {
      logger.warn(
          "Error, invalid parameters for familyName: "
              + familyName
              + " givenName: "
              + givenName
              + ".");
      throw new BadArgumentException("KO, invalid argument.");
    }
    logger.trace("Get patient: " + familyName + " - " + givenName);

    return patientService.getPatient(familyName, givenName);
  }

  /**
   * Update the given patient if exist. Can throw DataNotFound if it doesn't exist,
   * BadArgumentException if input data is incorrect
   *
   * @param patient the patient to update
   * @return the updated patient.
   */
  @Override
  @PutMapping("/update")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Patient updatePatient(
      @Valid @RequestBody UpdatePatientDto patient, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      System.out.println(patient);
      List<String> array = bindingResult.getFieldErrors().stream().map(FieldError::getField).toList();
        logger.warn("Error, invalid argument:" + array);
      throw new BadArgumentException("KO, invalid argument.");
    }
    System.out.println(patient);
    logger.trace(
        "Updating patient: " + patient.getFamilyName() + " - " + patient.getGivenName() + ".");
    return patientService.updatePatient(patient);
  }
}
