package com.mediscreen.patientinfo.service;

import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.model.dto.CreatePatientDto;
import com.mediscreen.patientinfo.model.dto.UpdatePatientDto;

/**
 * Service for patient feature.
 *
 * <p>contains method used for CRUD on patient entity.
 */
public interface PatientService {

  /**
   * Retrieve the Patient information from repository based on its lastname and firstname.
   *
   * @param familyName the patient lastname
   * @param givenName the patient firstname
   * @return the patient information
   */
  Patient getPatient(String familyName, String givenName);

  /**
   * Update the given patient.
   *
   * @param updatePatientDto the patient data to update
   * @return the updated patient
   */
  Patient updatePatient(UpdatePatientDto updatePatientDto);

  /**
   * Create a new patient.
   *
   * @param createPatientDto the patient to create
   * @return the patient saved in db
   */
  Patient createPatient(CreatePatientDto createPatientDto);
}
