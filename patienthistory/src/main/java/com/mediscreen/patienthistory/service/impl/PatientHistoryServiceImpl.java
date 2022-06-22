package com.mediscreen.patienthistory.service.impl;

import com.mediscreen.patienthistory.exception.DataNotFoundException;
import com.mediscreen.patienthistory.model.History;
import com.mediscreen.patienthistory.model.Note;
import com.mediscreen.patienthistory.model.dto.UpdateHistoryDto;
import com.mediscreen.patienthistory.repository.PatientHistoryRepository;
import com.mediscreen.patienthistory.service.PatientHistoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Implementation for PatientHistoryService. */
@Service
public class PatientHistoryServiceImpl implements PatientHistoryService {
  private final Logger logger = LoggerFactory.getLogger(PatientHistoryServiceImpl.class);
  @Autowired PatientHistoryRepository patientHistoryRepository;

  /**
   * Get all the notes saved for the given patient.
   *
   * @param familyName the patient family namenew ObjectMapper()
   * @param givenName the patient given name
   * @return collection of Note
   */
  @Override
  public History getPatientHistory(String familyName, String givenName) {
    logger.trace("service, get patient history in db.");
    Optional<History> history =
        patientHistoryRepository.findHistoryByFamilyNameAndGivenName(familyName, givenName);
    if (history.isEmpty()) {
      logger.warn("Error, patient: " + familyName + " - " + givenName + " doesn't exist.");
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
    Optional<History> savedHistory =
        patientHistoryRepository.findHistoryByPatientId(updateHistoryDto.getPatientId());
    // throw DataNotFoundException if Optional.empty
    if (savedHistory.isEmpty()) {
      logger.warn("Error, can't find patient history for id: " + updateHistoryDto.getPatientId());
      throw new DataNotFoundException("KO, patient history doesn't exist.");
    }
    History patientHistory = savedHistory.get();
    int count = 0;
    // Update givenName/familyName if !=
    if (!updateHistoryDto.getFamilyName().isBlank()
        && !updateHistoryDto.getFamilyName().equals(patientHistory.getFamilyName())) {
      logger.trace("updating familyName");
      count++;
      patientHistory.setFamilyName(updateHistoryDto.getFamilyName());
    }
    if (!updateHistoryDto.getGivenName().isBlank()
        && !updateHistoryDto.getGivenName().equals(patientHistory.getGivenName())) {
      logger.trace("updating givenName");
      count++;
      patientHistory.setGivenName(updateHistoryDto.getGivenName());
    }
    // update note if dto.note.date == history.note.date
    if (!updateHistoryDto.getNotes().isEmpty() && patientHistory.getNotes().isEmpty()) {
      logger.trace("adding text note.");
      count++;
      patientHistory.setNotes(updateHistoryDto.getNotes());
    } else if (!updateHistoryDto.getNotes().isEmpty()) {
      for (Note upNote : updateHistoryDto.getNotes()) {
        for (Note note : patientHistory.getNotes()) {
          if (note.getDate().equals(upNote.getDate())) {
            logger.trace("updating text note.");
            count++;
            note.setText(upNote.getText());
          }
        }
      }
    }
    logger.info(
        "Updating "
            + count
            + " fields for patient history with id="
            + patientHistory.getPatientId());
    return patientHistoryRepository.save(patientHistory);
  }
}
