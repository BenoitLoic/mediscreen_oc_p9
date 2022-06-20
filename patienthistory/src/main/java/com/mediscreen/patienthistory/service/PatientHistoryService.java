package com.mediscreen.patienthistory.service;

import com.mediscreen.patienthistory.model.History;
import com.mediscreen.patienthistory.model.Note;
import java.util.Collection;

public interface PatientHistoryService {
  /**
   * Get all the notes saved for the given patient.
   *
   * @param familyName the patient family name
   * @param givenName the patient given name
   * @return collection of Note
   */
  History getPatientHistory(String familyName, String givenName);
}
