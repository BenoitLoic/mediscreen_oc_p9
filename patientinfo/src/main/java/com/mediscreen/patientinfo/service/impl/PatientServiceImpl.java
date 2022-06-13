package com.mediscreen.patientinfo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mediscreen.patientinfo.exception.DataNotFoundException;
import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.repository.PatientRepository;
import com.mediscreen.patientinfo.service.PatientService;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

  Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);
  @Autowired private PatientRepository patientRepository;

  /**
   * Retrieve the Patient information from repository based on its lastname and firstname.
   *
   * @param familyName the patient lastname
   * @param givenName the patient firstname
   * @return the patient information
   */
  @Override
  public Patient getPatient(String familyName, String givenName) {
    Optional<Patient> patient =
        patientRepository.findByFamilyNameAndGivenName(familyName, givenName);
    if (patient.isEmpty()) {
      logger.warn(
          "Error, patient with familyName: "
              + familyName
              + " and givenName: "
              + givenName
              + " doesn't exist.");
      throw new DataNotFoundException("KO, patient doesn't exist.");
    }
    return patient.get();
  }
}
