package com.mediscreen.patientinfo.service;

import com.mediscreen.patientinfo.model.Patient;

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
}
