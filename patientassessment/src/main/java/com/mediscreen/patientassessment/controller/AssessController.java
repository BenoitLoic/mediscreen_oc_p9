package com.mediscreen.patientassessment.controller;

import com.mediscreen.patientassessment.model.Assessement;
import org.springframework.validation.BindingResult;

/**
 * Rest Controller for Assess feature.
 */
public interface AssessController {
  /**
   * Get assess for the given patient defined by its id.
   *
   * @param patientId the patient ID
   * @return the assessment
   */
  Assessement getAssessWithId(int patientId);

  /**
   * Get assess for the given patient defined by its familyName and givenName.
   *
   * @param familyName the patient's familyName
   * @param givenName the patient's givenName
   * @return the assessment
   */
  Assessement getAssessWithFamilyNameAndGivenName(String familyName,String givenName);
}
