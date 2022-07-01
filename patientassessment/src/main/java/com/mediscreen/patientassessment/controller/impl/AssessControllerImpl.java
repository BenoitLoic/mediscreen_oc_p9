package com.mediscreen.patientassessment.controller.impl;

import com.mediscreen.patientassessment.controller.AssessController;
import com.mediscreen.patientassessment.exception.BadArgumentException;
import com.mediscreen.patientassessment.model.Assessment;
import com.mediscreen.patientassessment.service.AssessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Implementation for AssessController. */
@RestController
@CrossOrigin
@RequestMapping("/assess")
public class AssessControllerImpl implements AssessController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired private AssessService assessService;

  /**
   * Get assess for the given patient defined by its id.
   *
   * @param patientId the patient ID
   * @return the assessment
   */
  @Override
  @PostMapping("/id")
  @ResponseStatus(HttpStatus.OK)
  public Assessment getAssessWithId(@RequestParam int patientId) {
    if (patientId <= 0) {
      logger.warn("Error, patientId must be > 0.");
      throw new BadArgumentException("KO, id is not valid.");
    }
    logger.trace("Get assess for id=" + patientId);
    return assessService.getAssessWithId(patientId);
  }

  /**
   * Get assess for the given patient defined by its familyName and givenName.
   *
   * @param familyName the patient's familyName
   * @param givenName the patient's givenName
   * @return the assessment
   */
  @Override
  @PostMapping("/name")
  @ResponseStatus(HttpStatus.OK)
  public Assessment getAssessWithFamilyNameAndGivenName(
      @RequestParam String familyName, @RequestParam String givenName) {
    if (familyName.isBlank() || givenName.isBlank()) {
      logger.warn("Error, patientId must be > 0.");
      throw new BadArgumentException("KO, id is not valid.");
    }
    logger.trace("Get assess for patient: " + familyName + " - " + givenName);
    return assessService.getAssessWithFamilyNameAndGivenName(familyName, givenName);
  }
}
