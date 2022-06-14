package com.mediscreen.patientinfo.service;

import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.model.dto.UpdatePatientDto;

/**
 * Service for patient feature.
 *
 * contains method used for CRUD on patient entity.
 */
public interface PatientService {

    /**
     *  Retrieve the Patient information from repository based on its lastname and firstname.
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
}
