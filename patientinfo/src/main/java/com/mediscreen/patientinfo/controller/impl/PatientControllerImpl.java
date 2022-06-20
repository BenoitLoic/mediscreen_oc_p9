package com.mediscreen.patientinfo.controller.impl;

import com.mediscreen.patientinfo.controller.PatientController;
import com.mediscreen.patientinfo.exception.BadArgumentException;
import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.model.dto.CreatePatientDto;
import com.mediscreen.patientinfo.model.dto.UpdatePatientDto;
import com.mediscreen.patientinfo.service.PatientService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Implementation for Patient Rest Controller. */
@RestController
@RequestMapping("/patient")
public class PatientControllerImpl implements PatientController {

  Logger logger = LoggerFactory.getLogger(PatientControllerImpl.class);
  @Autowired private PatientService patientService;

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
      List<String> array =
          bindingResult.getFieldErrors().stream().map(FieldError::getField).toList();
      logger.warn("Error, invalid argument:" + array);
      throw new BadArgumentException("KO, invalid argument.");
    }
    logger.trace(
        "Updating patient: " + patient.getFamilyName() + " - " + patient.getGivenName() + ".");
    return patientService.updatePatient(patient);
  }

  /**
   * Create a new patient. Can throw DataAlreadyExist if it already exists, BadArgumentException if
   * input data is incorrect
   *
   * @param patient the patient to create
   * @param bindingResult the binding result
   * @return the patient saved in db.
   */
  @Override
  @PostMapping("/add")
  @ResponseStatus(HttpStatus.CREATED)
  public Patient createPatient(
      @Valid @RequestBody CreatePatientDto patient, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      List<String> array =
          bindingResult.getFieldErrors().stream().map(FieldError::getField).toList();
      logger.warn("Error, invalid argument:" + array);
      throw new BadArgumentException("KO, invalid argument.");
    }
    logger.trace(
        "Creating patient: " + patient.getFamilyName() + " - " + patient.getGivenName() + ".");
    return patientService.createPatient(patient);
  }
}
