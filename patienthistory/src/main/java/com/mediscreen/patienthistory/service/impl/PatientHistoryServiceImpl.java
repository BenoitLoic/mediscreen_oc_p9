package com.mediscreen.patienthistory.service.impl;

import com.mediscreen.patienthistory.exception.DataNotFoundException;
import com.mediscreen.patienthistory.model.History;
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
   * @param familyName the patient family name
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
}
