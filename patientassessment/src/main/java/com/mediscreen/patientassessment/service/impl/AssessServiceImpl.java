package com.mediscreen.patientassessment.service.impl;

import com.mediscreen.patientassessment.constant.AssessMessages;
import com.mediscreen.patientassessment.constant.TriggerKeywords;
import com.mediscreen.patientassessment.exception.DataNotFoundException;
import com.mediscreen.patientassessment.feign.PatientInfoClient;
import com.mediscreen.patientassessment.model.Assessment;
import com.mediscreen.patientassessment.model.History;
import com.mediscreen.patientassessment.model.Note;
import com.mediscreen.patientassessment.model.PatientInfo;
import com.mediscreen.patientassessment.repository.PatientHistoryRepository;
import com.mediscreen.patientassessment.service.AssessService;
import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AssessServiceImpl implements AssessService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired private PatientInfoClient patientInfoClient;
  @Autowired private PatientHistoryRepository patientHistoryRepository;

  /**
   * Get the patient assess defined by its ID.
   *
   * @param patientId the patient ID
   * @return the assessement
   */
  @Override
  public Assessment getAssessWithId(int patientId) {

    // get patient info
    PatientInfo patientInfo = patientInfoClient.getPatientByID(patientId);
    // get patient history
    Optional<History> history = patientHistoryRepository.findHistoryByPatientId(patientId);
    if (patientInfo == null || history.isEmpty()) {
      logger.warn("Error, can't find patient with id=" + patientId);
      throw new DataNotFoundException("KO, patient doesn't exist.");
    }

    // calculate keyword occurrence
    AtomicInteger keywordCount = new AtomicInteger();
    for (Note note : history.get().getNotes()) {
      TriggerKeywords.keywords.forEach(
          word -> {
            if (StringUtils.countOccurrencesOf(note.getText().toLowerCase(), word) > 0) {
              keywordCount.getAndIncrement();
            }
          });
    }
    System.out.println("keyword : "+keywordCount);
    logger.trace("keyword count = " + keywordCount);

    // calculate age
    LocalDate birthDate = patientInfo.getBirthDate();
    int age = LocalDate.now().getYear() - birthDate.getYear();
    logger.trace("age = " + age);
    System.out.println("age : " + age);
    // get sex
    String sex = patientInfo.getSex();
    System.out.println("sex : " + sex);

    // create to Assessment
    Assessment assessment = new Assessment();
    assessment.setAge(age);
    assessment.setFamilyName(patientInfo.getFamilyName());
    assessment.setGivenName(patientInfo.getGivenName());
    // switch

    if (keywordCount.get() == 0) {
      // risk None
      assessment.setMessage(AssessMessages.NONE);
    } else if (keywordCount.get() == 2 && age >= 30) {
      // risk Borderline
      assessment.setMessage(AssessMessages.BORDERLINE);
    } else if (keywordCount.get() == 3 && age < 30 && sex.equals("M")) {
      // risk InDanger
      assessment.setMessage(AssessMessages.IN_DANGER);
    } else if (keywordCount.get() == 4 && age < 30 && sex.equals("F")) {
      // risk InDanger
      assessment.setMessage(AssessMessages.IN_DANGER);
    } else if (keywordCount.get() == 6 && age >= 30) {
      // risk InDanger
      assessment.setMessage(AssessMessages.IN_DANGER);
    } else if (keywordCount.get() == 5 && age < 30 && sex.equals("M")) {
      // risk EarlyOnset
      assessment.setMessage(AssessMessages.EARLY_ONSET);
    } else if (keywordCount.get() == 7 && age < 30 && sex.equals("F")) {
      // risk EarlyOnset
      assessment.setMessage(AssessMessages.EARLY_ONSET);
    } else if (keywordCount.get() == 8 && age >= 30) {
      // risk EarlyOnset
      assessment.setMessage(AssessMessages.EARLY_ONSET);
    }
    logger.trace("Assess message = " + assessment.getMessage());
    System.out.println(assessment.getMessage());
    // return result
    return assessment;
  }

  /**
   * Get the patient assess defined by its family-name and given-name.
   *
   * @param familyName the patient family-name
   * @param givenName the patient given-name
   * @return the assessment
   */
  @Override
  public Assessment getAssessWithFamilyNameAndGivenName(String familyName, String givenName) {
    return null;
  }
}
