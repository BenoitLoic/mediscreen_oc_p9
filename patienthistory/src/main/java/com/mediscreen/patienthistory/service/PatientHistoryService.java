package com.mediscreen.patienthistory.service;

import com.mediscreen.patienthistory.model.History;
import com.mediscreen.patienthistory.model.dto.UpdateHistoryDto;

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

  /**
   * Update an existing patient History.
   *
   * @param updateHistoryDto the history to update.
   * @return the updated History.
   */
  History updatePatientHistory(UpdateHistoryDto updateHistoryDto);
}
