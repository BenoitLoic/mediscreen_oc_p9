package com.mediscreen.patienthistory.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.mediscreen.patienthistory.controller.PatientHistoryController;
import com.mediscreen.patienthistory.exception.BadArgumentException;
import com.mediscreen.patienthistory.model.History;
import com.mediscreen.patienthistory.model.Note;
import com.mediscreen.patienthistory.service.PatientHistoryService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

@RestController
@RequestMapping("/patHistory")
public class PatientHistoryControllerImpl implements PatientHistoryController {

  Logger logger = LoggerFactory.getLogger(PatientHistoryControllerImpl.class);

  @Autowired private PatientHistoryService patientHistoryService;

  /**
   * Get all Notes for the given patient. Can throw BadArgumentException if argument is blank or
   * null.
   *
   * @param familyName the patient's lastname
   * @param givenName the patient's firstname
   * @return collection with all saved notes
   */
  @Override
  @GetMapping("/get")
  @ResponseStatus(HttpStatus.OK)
  public History getPatientHistory(
      @RequestParam String familyName, @RequestParam String givenName) {

    if (familyName.isBlank() || givenName.isBlank()) {
      logger.warn(
          "Error, invalid argument for familyName:'"
              + familyName
              + "' - givenName:'"
              + givenName
              + "'. error: can't be null or blank.");
      throw new BadArgumentException("KO, invalid argument.");
    }
    logger.trace("Getting history for patient: " + givenName + " - " + familyName + ".");

    logger.info(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());

    return patientHistoryService.getPatientHistory(familyName, givenName);
  }
}
