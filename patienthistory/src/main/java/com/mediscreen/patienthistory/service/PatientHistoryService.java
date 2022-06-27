package com.mediscreen.patienthistory.service;

import com.mediscreen.patienthistory.model.History;
import com.mediscreen.patienthistory.model.dto.AddNoteDto;
import com.mediscreen.patienthistory.model.dto.AddPatientHistoryDto;
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

  /**
   * Create a new History.
   * Can throw DataAlreadyExistException if history already exist in db.
   *
   * @param addPatientHistoryDto the history to create.
   * @return the History saved.
   */
  History createPatientHistory(AddPatientHistoryDto addPatientHistoryDto);

  /**
   * Create a new Note for the given History
   * @param addNoteDto the note to add
   * @return the patient's history
   */
  History createPatientHistoryNote(AddNoteDto addNoteDto);
}
