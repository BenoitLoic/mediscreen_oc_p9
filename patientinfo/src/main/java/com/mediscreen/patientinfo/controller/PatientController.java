package com.mediscreen.patientinfo.controller;

import org.springframework.validation.BindingResult;
import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.model.dto.UpdatePatientDto;

/** Patient Controller, contain CRUD method for patient entity. */
public interface PatientController {
  /**
   * Get the patient with the given familyName (lastname) and givenName (firstname).
   *
   * @param familyName the patient family name
   * @param givenName the patient given name
   * @return the patient information
   */
  Patient getPatient(String familyName, String givenName);

  /**
   * Update the given patient if exist.
   * Can throw DataNotFound if it doesn't exist, BadArgumentException if input data is incorrect
   *
   * @param patient the patient to update
   * @return the updated patient.
   */
  Patient updatePatient(UpdatePatientDto patient, BindingResult bindingResult);
}
