package com.mediscreen.patienthistory.controller;

import com.mediscreen.patienthistory.model.History;

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
}
