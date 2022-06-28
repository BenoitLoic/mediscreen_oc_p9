package com.mediscreen.patientassessment.service;

import com.mediscreen.patientassessment.model.Assessement;

public interface AssessService {
    /**
     * Get the patient assess defined by its ID.
     *
     * @param patientId the patient ID
     * @return the assessement
     */
    Assessement getAssessWithId(int patientId);

    /**
     * Get the patient assess defined by its family-name and given-name.
     *
     * @param familyName the patient family-name
     * @param givenName the patient given-name
     * @return the assessment
     */
    Assessement getAssessWithFamilyNameAndGivenName(String familyName, String givenName);
}
