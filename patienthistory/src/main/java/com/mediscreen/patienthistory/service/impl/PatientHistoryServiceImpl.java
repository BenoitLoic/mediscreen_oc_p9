package com.mediscreen.patienthistory.service.impl;

import com.mediscreen.patienthistory.exception.DataAlreadyExistException;
import com.mediscreen.patienthistory.exception.DataNotFoundException;
import com.mediscreen.patienthistory.model.History;
import com.mediscreen.patienthistory.model.Note;
import com.mediscreen.patienthistory.model.dto.AddNoteDto;
import com.mediscreen.patienthistory.model.dto.AddPatientHistoryDto;
import com.mediscreen.patienthistory.model.dto.UpdateHistoryDto;
import com.mediscreen.patienthistory.model.dto.UpdateNoteDto;
import com.mediscreen.patienthistory.repository.PatientHistoryRepository;
import com.mediscreen.patienthistory.service.PatientHistoryService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Implementation for PatientHistoryService. */
@Service
public class PatientHistoryServiceImpl implements PatientHistoryService {
  private final Logger logger = LoggerFactory.getLogger(PatientHistoryServiceImpl.class);
  @Autowired private PatientHistoryRepository patientHistoryRepository;

  /**
   * Get all the notes saved for the given patient.
   *
   * @param familyName the patient family namenew ObjectMapper()
   * @param givenName the patient given name
   * @return collection of Note
   */
  @Override
  public History getPatientHistoryByFamilyNameAndGivenName(String familyName, String givenName) {
    logger.trace("service, get patient history in db.");
    Optional<History> history =
        patientHistoryRepository.findHistoryByFamilyNameIgnoreCaseAndGivenNameIgnoreCase(
            familyName, givenName);
    if (history.isEmpty()) {
      logger.warn("Error, patient: " + familyName + " - " + givenName + " doesn't exist.");
      throw new DataNotFoundException("KO, patient doesn't exist.");
    }
    return history.get();
  }

  /**
   * Get history saved for the given patient.
   *
   * @param patientId the patient id
   * @return collection of Note
   */
  @Override
  public History getPatientHistoryById(int patientId) {
    logger.trace("service, get patient history in db.");
    Optional<History> history = patientHistoryRepository.findHistoryByPatientId(patientId);
    if (history.isEmpty()) {
      logger.warn("Error, patient with id= " + patientId + " doesn't exist.");
      throw new DataNotFoundException("KO, patient doesn't exist.");
    }
    return history.get();
  }

  /**
   * Update an existing patient History. This method only update nonNull and nonBlank fields. can
   * throw DataNotFoundException if there is no History for the given patientId.
   *
   * @param updateHistoryDto the history to update.
   * @return the updated History.
   */
  @Override
  public History updatePatientHistory(UpdateHistoryDto updateHistoryDto) {

    // Get History from db
    History patientHistory = this.getPatientHistoryById(updateHistoryDto.getPatientId());
    // Update givenName/familyName if !=
    if (!updateHistoryDto.getFamilyName().isBlank()
        && !updateHistoryDto.getFamilyName().equals(patientHistory.getFamilyName())) {
      logger.trace("updating familyName");
      patientHistory.setFamilyName(updateHistoryDto.getFamilyName());
    }
    if (!updateHistoryDto.getGivenName().isBlank()
        && !updateHistoryDto.getGivenName().equals(patientHistory.getGivenName())) {
      logger.trace("updating givenName");
      patientHistory.setGivenName(updateHistoryDto.getGivenName());
    }
    logger.info("Updating patient history with id=" + patientHistory.getPatientId());
    return patientHistoryRepository.save(patientHistory);
  }

  /**
   * Create a new History. Can throw DataAlreadyExistException if history.patientId already exist in
   * db.
   *
   * @param addPatientHistoryDto the history to create.
   * @return the History saved.
   */
  @Override
  public History createPatientHistory(AddPatientHistoryDto addPatientHistoryDto) {

    // check if history already exist
    Optional<History> savedHistory =
        patientHistoryRepository.findHistoryByPatientId(addPatientHistoryDto.getPatientId());
    if (savedHistory.isPresent()) {
      logger.warn(
          "Error, History already exist for patient id=" + addPatientHistoryDto.getPatientId());
      throw new DataAlreadyExistException("KO, patientHistory already exist.");
    }
    // map dto to History
    History history = new History();
    BeanUtils.copyProperties(addPatientHistoryDto, history);
    if (!addPatientHistoryDto.getTextNote().isBlank()) {
      Note note = new Note();
      note.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
      note.setText(addPatientHistoryDto.getTextNote());
      history.setNotes(List.of(note));
    }
    // save
    logger.trace("Saving new history.");
    return patientHistoryRepository.save(history);
  }

  /**
   * Create a new Note for the given History.
   *
   * @param addNoteDto the note to add
   * @return the patient's history
   */
  @Override
  public History createPatientHistoryNote(AddNoteDto addNoteDto) {
    // check if history exist
    History historyToSave = this.getPatientHistoryById(addNoteDto.getPatientId());

    Note noteToAdd = new Note();
    noteToAdd.setText(addNoteDto.getTextNote());
    noteToAdd.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

    Collection<Note> notes = new ArrayList<>(historyToSave.getNotes());
    notes.add(noteToAdd);
    historyToSave.setNotes(notes);
    // save
    return patientHistoryRepository.save(historyToSave);
  }

  /**
   * Update a Note from an existing Patient History.
   *
   * @param updateNoteDto the note to update.
   * @return the patient history updated.
   */
  @Override
  public History updatePatientHistoryNote(UpdateNoteDto updateNoteDto) {
    // check if history exist

    History history = this.getPatientHistoryById(updateNoteDto.getPatientId());
    for (Note note : history.getNotes()) {
      if (note.getDate().equals(updateNoteDto.getDate())) {
        logger.trace("Updating text.");
        note.setText(updateNoteDto.getText());
        break;
      }
    }

    return patientHistoryRepository.save(history);
  }
}
