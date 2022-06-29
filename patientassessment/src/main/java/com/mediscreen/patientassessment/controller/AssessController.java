package com.mediscreen.patientassessment.controller;

import com.mediscreen.patientassessment.model.Assessment;

/** Rest Controller for Assess feature. */
public interface AssessController {
  /**
   * Get assess for the given patient defined by its id.
   *
   * @param patientId the patient ID
   * @return the assessment
   */
  Assessment getAssessWithId(int patientId);

  /**
   * Get assess for the given patient defined by its familyName and givenName.
   *
   * @param familyName the patient's familyName
   * @param givenName the patient's givenName
   * @return the assessment
   */
  Assessment getAssessWithFamilyNameAndGivenName(String familyName, String givenName);
}
