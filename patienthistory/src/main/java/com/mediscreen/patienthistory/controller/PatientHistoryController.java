package com.mediscreen.patienthistory.controller;

import java.util.Collection;
import org.springframework.validation.BindingResult;
import com.mediscreen.patienthistory.model.History;
import com.mediscreen.patienthistory.model.Note;

/**
 * Interface for Patient History Rest API.
 */
public interface PatientHistoryController {

    /**
     * Get all Notes for the given patient.
     * Can throw BadArgumentException if argument is blank or null.
     *
     * @param familyName the patient's lastname
     * @param givenName the patient's firstname
     * @return collection with all saved notes
     */
    History getPatientHistory(String familyName, String givenName);

}
