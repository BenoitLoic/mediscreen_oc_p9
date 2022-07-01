package com.mediscreen.patientassessment.service.impl;

import com.mediscreen.patientassessment.constant.AssessMessages;
import com.mediscreen.patientassessment.constant.TriggerKeywords;
import com.mediscreen.patientassessment.constant.TriggerKeywordsOccurrenceCount;
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

/**
 * Assess service implementation.
 * Contains method that calculate and return the assessment.
 */
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

    PatientInfo patientInfo = getPatientInfoById(patientId);
    History patientHistory = getHistoryById(patientId);
    return getAssessment(patientInfo, patientHistory);
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
    PatientInfo patientInfo = getPatientInfoByFamilyNameAndGivenName(familyName, givenName);
    History patientHistory = getHistoryByFamilyNameAndGivenName(familyName, givenName);
    return getAssessment(patientInfo, patientHistory);
  }

  private Assessment getAssessment(PatientInfo patientInfo, History history) {
    int keywordCount = getKeywordCount(history);
    int age = calculateAge(patientInfo);
    String sex = patientInfo.getSex();
    Assessment assessment = generateAssessment(patientInfo, keywordCount, age, sex);
    logger.trace("Assess message = " + assessment.getMessage());
    return assessment;
  }

  private History getHistoryById(int patientId) {
    Optional<History> historyOptional = patientHistoryRepository.findHistoryByPatientId(patientId);
    if (historyOptional.isEmpty()) {
      logger.warn("Error, can't find patient history with id=" + patientId);
      throw new DataNotFoundException("KO, patient doesn't exist.");
    }
    return historyOptional.get();
  }

  private History getHistoryByFamilyNameAndGivenName(String familyName, String givenName) {
    Optional<History> historyOptional =
        patientHistoryRepository.findHistoryByFamilyNameAndGivenName(familyName, givenName);
    if (historyOptional.isEmpty()) {
      logger.warn("Error, can't find patient history for: " + familyName + " - " + givenName);
      throw new DataNotFoundException("KO, patient doesn't exist.");
    }
    return historyOptional.get();
  }

  private PatientInfo getPatientInfoById(int patientId) {
    PatientInfo patientInfo = patientInfoClient.getPatientByID(patientId);
    if (patientInfo == null) {
      logger.warn("Error, can't find patient with id=" + patientId);
      throw new DataNotFoundException("KO, patient doesn't exist.");
    }
    return patientInfo;
  }

  private PatientInfo getPatientInfoByFamilyNameAndGivenName(String familyName, String givenName) {
    PatientInfo patientInfo =
        patientInfoClient.getPatientByFamilyNameAndGivenName(familyName, givenName);
    if (patientInfo == null) {
      logger.warn("Error, can't find patient : " + familyName + " - " + givenName);
      throw new DataNotFoundException("KO, patient doesn't exist.");
    }
    return patientInfo;
  }

  private Assessment generateAssessment(
      PatientInfo patientInfo, int keywordCount, int age, String sex) {
    // create to Assessment
    Assessment assessment = new Assessment();
    assessment.setAge(age);
    assessment.setFamilyName(patientInfo.getFamilyName());
    assessment.setGivenName(patientInfo.getGivenName());
    // switch

    String male = "M";
    String female = "F";

    if (age >= 30) {
      assessAnySexOlderThan30(keywordCount, assessment);
    } else {
      if (sex.equals(male)) {
        assessMaleYoungerThan30(keywordCount, assessment);
      } else {
        assessFemaleYoungerThan30(keywordCount, sex, assessment, female);
      }
    }
    return assessment;
  }

  private void assessAnySexOlderThan30(int keywordCount, Assessment assessment) {
    if (keywordCount < TriggerKeywordsOccurrenceCount.ANY_SEX_OLDER_THAN_30_BORDERLINE) {
      // NONE
      assessment.setMessage(AssessMessages.NONE);
    } else if (keywordCount < TriggerKeywordsOccurrenceCount.ANY_SEX_OLDER_THAN_30_IN_DANGER) {
      // Borderline
      assessment.setMessage(AssessMessages.BORDERLINE);
    } else if (keywordCount < TriggerKeywordsOccurrenceCount.ANY_SEX_OLDER_THAN_30_EARLY_ONSET) {
      // InDanger
      assessment.setMessage(AssessMessages.IN_DANGER);
    } else {
      // EarlyOnset
      assessment.setMessage(AssessMessages.EARLY_ONSET);
    }
  }

  private void assessFemaleYoungerThan30(
      int keywordCount, String sex, Assessment assessment, String female) {
    if (sex.equals(female)) {
      if (keywordCount < TriggerKeywordsOccurrenceCount.FEMALE_YOUNGER_THAN_30_BORDERLINE) {
        assessment.setMessage(AssessMessages.NONE);
      } else if (keywordCount < TriggerKeywordsOccurrenceCount.FEMALE_YOUNGER_THAN_30_IN_DANGER) {
        assessment.setMessage(AssessMessages.BORDERLINE);
      } else if (keywordCount < TriggerKeywordsOccurrenceCount.FEMALE_YOUNGER_THAN_30_EARLY_ONSET) {
        assessment.setMessage(AssessMessages.IN_DANGER);
      } else {
        assessment.setMessage(AssessMessages.EARLY_ONSET);
      }
    }
  }

  private void assessMaleYoungerThan30(int keywordCount, Assessment assessment) {
    if (keywordCount < TriggerKeywordsOccurrenceCount.MALE_YOUNGER_THAN_30_BORDERLINE) {
      assessment.setMessage(AssessMessages.NONE);
    } else if (keywordCount < TriggerKeywordsOccurrenceCount.MALE_YOUNGER_THAN_30_IN_DANGER) {
      assessment.setMessage(AssessMessages.BORDERLINE);
    } else if (keywordCount < TriggerKeywordsOccurrenceCount.MALE_YOUNGER_THAN_30_EARLY_ONSET) {
      assessment.setMessage(AssessMessages.IN_DANGER);
    } else {
      assessment.setMessage(AssessMessages.EARLY_ONSET);
    }
  }

  private int calculateAge(PatientInfo patientInfo) {
    LocalDate birthDate = patientInfo.getBirthDate();
    return LocalDate.now().getYear() - birthDate.getYear();
  }

  private int getKeywordCount(History history) {
    AtomicInteger keywordCount = new AtomicInteger();
    for (Note note : history.getNotes()) {
      TriggerKeywords.KEYWORDS.forEach(
          word -> {
            if (StringUtils.countOccurrencesOf(note.getText().toLowerCase(), word) > 0) {
              keywordCount.getAndIncrement();
            }
          });
    }
    logger.trace("keyword count = " + keywordCount);
    return keywordCount.get();
  }
}
