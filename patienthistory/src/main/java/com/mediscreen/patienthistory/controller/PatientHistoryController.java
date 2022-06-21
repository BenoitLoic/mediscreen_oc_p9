package com.mediscreen.patienthistory.controller;

import com.mediscreen.patienthistory.model.History;
import com.mediscreen.patienthistory.model.dto.UpdateHistoryDto;
import org.springframework.validation.BindingResult;

/** Interface for Patient History Rest API. */
public interface PatientHistoryController {

  /**
   * Get all Notes for the given patient. Can throw BadArgumentException if argument is blank or
   * null.
   *
   * @param familyName the patient's lastname
   * @param givenName the patient's firstname
   * @return collection with all saved notes
   */
  History getPatientHistory(String familyName, String givenName);

  /**
   * Update the patient history based on the patient ID. Can throw BadArgumentException if either
   * patientId, givenName or familyName is null or blank.
   *
   * @param updateHistoryDto the history to update
   * @return the updated history
   */
  History updatePatientHistory(UpdateHistoryDto updateHistoryDto, BindingResult bindingResult);
}
