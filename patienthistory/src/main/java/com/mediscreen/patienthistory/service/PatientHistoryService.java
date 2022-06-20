package com.mediscreen.patienthistory.service;

import com.mediscreen.patienthistory.model.History;

/**
 * Service for patientHistory feature.
 *
 * <p>contains method used for CRUD on history entity.
 */
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
