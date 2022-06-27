package com.mediscreen.patienthistory.controller;

import com.mediscreen.patienthistory.model.History;
import com.mediscreen.patienthistory.model.dto.AddNoteDto;
import com.mediscreen.patienthistory.model.dto.AddPatientHistoryDto;
import com.mediscreen.patienthistory.model.dto.UpdateHistoryDto;
import com.mediscreen.patienthistory.model.dto.UpdateNoteDto;
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

  /**
   * Create a new patient history.Can throw BadArgumentException if either patientId, givenName or
   * familyName is null or blank. Can throw DataAlreadyExistException if patientId is already
   * present in db.
   *
   * @param addPatientHistoryDto the history to create
   * @return the history created
   */
  History createPatientHistory(
      AddPatientHistoryDto addPatientHistoryDto, BindingResult bindingResult);

  /**
   * Create a new Note. Can throw BadArgumentException if either patientId or text is null.
   *
   * @param addNoteDto the note to create
   * @return the patient history
   */
  History addNote(AddNoteDto addNoteDto,BindingResult bindingResult);

  /**
   * Update the note from an existing patient history.
   *
   * @param updateNoteDto the note to update.
   * @return the updated history
   */
  History updateNote(UpdateNoteDto updateNoteDto,BindingResult bindingResult);
}
