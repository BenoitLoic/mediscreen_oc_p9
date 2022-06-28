package com.mediscreen.patientassessment.service.impl;

import com.mediscreen.patientassessment.model.Assessement;
import com.mediscreen.patientassessment.service.AssessService;
import org.springframework.stereotype.Service;

@Service
public class AssessServiceImpl implements AssessService {

    /**
     * Get the patient assess defined by its ID.
     *
     * @param patientId the patient ID
     * @return the assessement
     */
    @Override
    public Assessement getAssessWithId(int patientId) {
        return null;
    }

    /**
     * Get the patient assess defined by its family-name and given-name.
     *
     * @param familyName the patient family-name
     * @param givenName  the patient given-name
     * @return the assessment
     */
    @Override
    public Assessement getAssessWithFamilyNameAndGivenName(String familyName, String givenName) {
        return null;
    }
}
