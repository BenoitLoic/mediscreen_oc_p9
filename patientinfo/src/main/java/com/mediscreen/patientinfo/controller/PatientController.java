package com.mediscreen.patientinfo.controller;

import com.mediscreen.patientinfo.model.Patient;

/** Patient Controller, contain CRUD method for patient entity. */
public interface PatientController {
  /**
   * Get the patient with the given familyName (lastname) and givenName (firstname).
   *
   * @param familyName the patient family name
   * @param givenName the patient given name
   * @return the patient information
   */
  Patient getPatient(String familyName, String givenName);
}
