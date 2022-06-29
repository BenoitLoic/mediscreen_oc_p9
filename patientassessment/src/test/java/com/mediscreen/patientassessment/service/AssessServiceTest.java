package com.mediscreen.patientassessment.service;

import com.mediscreen.patientassessment.constant.AssessMessages;
import com.mediscreen.patientassessment.feign.PatientInfoClient;
import com.mediscreen.patientassessment.model.Assessment;
import com.mediscreen.patientassessment.model.History;
import com.mediscreen.patientassessment.model.Note;
import com.mediscreen.patientassessment.model.PatientInfo;
import com.mediscreen.patientassessment.repository.PatientHistoryRepository;
import com.mediscreen.patientassessment.service.impl.AssessServiceImpl;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssessServiceTest {

  private final String givenName = "givenNameTest";
  private final String familyName = "familyNameTest";
  private final int patientId = 2;
  private String message;

  private final PatientInfo twentyYearOldMale = new PatientInfo();
  private final PatientInfo twentyYearOldFemale = new PatientInfo();
  private final PatientInfo fiftyYearOldMale = new PatientInfo();
  private final Note noKeywordNote = new Note();
  private final Note twoKeywordNote = new Note();
  private final Note threeKeywordNote = new Note();
  private History patientHistory;

  @Mock private PatientHistoryRepository patientHistoryRepoMock;
  @Mock private PatientInfoClient patientInfoClientMock;
  @InjectMocks AssessServiceImpl assessService;

  @BeforeEach
  void setUp() {

    twentyYearOldMale.setFamilyName(familyName);
    twentyYearOldMale.setGivenName(givenName);
    twentyYearOldMale.setId(patientId);
    twentyYearOldMale.setBirthDate(LocalDate.now().minusYears(20));
    twentyYearOldMale.setSex("M");

    twentyYearOldFemale.setFamilyName(familyName);
    twentyYearOldFemale.setGivenName(givenName);
    twentyYearOldFemale.setId(patientId);
    twentyYearOldFemale.setBirthDate(LocalDate.now().minusYears(20));
    twentyYearOldFemale.setSex("F");

    fiftyYearOldMale.setFamilyName(familyName);
    fiftyYearOldMale.setGivenName(givenName);
    fiftyYearOldMale.setId(patientId);
    fiftyYearOldMale.setBirthDate(LocalDate.now().minusYears(50));
    fiftyYearOldMale.setSex("M");

    String noKeyword =
        "Lorem ipsum dolor sit amet, "
            + "consectetur adipiscing elit,"
            + " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
    noKeywordNote.setText(noKeyword);
    String twoKeywords =
        "Duis aute irure dolor Fumeur in reprehenderit in "
            + "voluptate velit esse cillum dolore eu rechute fugiat nulla pariatur.";
    twoKeywordNote.setText(twoKeywords);
    String threeKeywords =
        "At vero eos et accusamus et iusto odio dignissimos "
            + "ducimus qui blanditiis praesentium Hémoglobine A1C voluptatum deleniti atque corrupti quos dolores "
            + "et quas molestias excepturi sint occaecati cupiditate non provident, similique "
            + "sunt in culpa qui Microalbumine officia deserunt Poids mollitia animi, "
            + "id est laborum et dolorum fuga.";
    threeKeywordNote.setText(threeKeywords);
    patientHistory = new History();
  }
  /*
   * Risk = None : NO keywords.
   * Risk = Borderline : 2 keywords + age > 30.
   * Risk = InDanger : 3 keywords + age < 30 + sex == M.
   * Risk = InDanger : 4 keywords + age < 30 + sex == F.
   * Risk = InDanger : 6 keywords + age > 30.
   * Risk = EarlyOnset : 5 keywords + age < 30 + sex == M.
   * Risk = EarlyOnset : 7 keywords + age < 30 + sex == F.
   * Risk = EarlyOnset : 8 <= keywords + age > 30.
   */

  // Risk = None : NO keywords.
  @Test
  void getAssessWithId_ShouldReturnNone() {

    // GIVEN
    patientHistory.setNotes(List.of(noKeywordNote));

    Assessment expected = new Assessment();
    expected.setAge(50);
    expected.setFamilyName(familyName);
    expected.setGivenName(givenName);
    message = AssessMessages.NONE;
    expected.setMessage(message);
    // WHEN
    when(patientHistoryRepoMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.of(patientHistory));
    when(patientInfoClientMock.getPatientByID(anyInt())).thenReturn(fiftyYearOldMale);
    // THEN
    var actual = assessService.getAssessWithId(patientId);
    assertEquals(expected, actual);
  }
  // Risk = Borderline : 2 keywords + age > 30.
  @Test
  void getAssessWithId_ShouldReturnBorderline() {

    // GIVEN
    patientHistory.setNotes(List.of(twoKeywordNote));

    Assessment expected = new Assessment();
    expected.setAge(50);
    expected.setFamilyName(familyName);
    expected.setGivenName(givenName);
    message = AssessMessages.BORDERLINE;
    expected.setMessage(message);
    // WHEN
    when(patientHistoryRepoMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.of(patientHistory));
    when(patientInfoClientMock.getPatientByID(anyInt())).thenReturn(fiftyYearOldMale);
    // THEN
    Assessment actual = assessService.getAssessWithId(patientId);
    assertEquals(expected, actual);
  }
  // Risk = InDanger : 3 keywords + age < 30 + sex == M.
  @Test
  void getAssessWithId_Male_ShouldReturnInDanger() {

    // GIVEN
    patientHistory.setNotes(List.of(threeKeywordNote));

    Assessment expected = new Assessment();
    expected.setAge(20);
    expected.setFamilyName(familyName);
    expected.setGivenName(givenName);
    message = AssessMessages.IN_DANGER;
    expected.setMessage(message);
    // WHEN
    when(patientHistoryRepoMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.of(patientHistory));
    when(patientInfoClientMock.getPatientByID(anyInt())).thenReturn(twentyYearOldMale);
    // THEN
    Assessment actual = assessService.getAssessWithId(patientId);
    assertEquals(expected, actual);
  }
  //  Risk = InDanger : 4 keywords + age < 30 + sex == F.
  @Test
  void getAssessWithId_Female_ShouldReturnInDanger() {

    // GIVEN
    patientHistory.setNotes(List.of(twoKeywordNote, twoKeywordNote));

    Assessment expected = new Assessment();
    expected.setAge(20);
    expected.setFamilyName(familyName);
    expected.setGivenName(givenName);
    message = AssessMessages.IN_DANGER;
    expected.setMessage(message);
    // WHEN
    when(patientHistoryRepoMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.of(patientHistory));
    when(patientInfoClientMock.getPatientByID(anyInt())).thenReturn(twentyYearOldFemale);
    // THEN
    Assessment actual = assessService.getAssessWithId(patientId);
    assertEquals(expected, actual);
  }
  //  Risk = InDanger : 6 keywords + age > 30.
  @Test
  void getAssessWithId_Both_ShouldReturnInDanger() {

    // GIVEN
    patientHistory.setNotes(List.of(threeKeywordNote, threeKeywordNote));

    Assessment expected = new Assessment();
    expected.setAge(50);
    expected.setFamilyName(familyName);
    expected.setGivenName(givenName);
    message = AssessMessages.IN_DANGER;
    expected.setMessage(message);
    // WHEN
    when(patientHistoryRepoMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.of(patientHistory));
    when(patientInfoClientMock.getPatientByID(anyInt())).thenReturn(fiftyYearOldMale);
    // THEN
    Assessment actual = assessService.getAssessWithId(patientId);
    assertEquals(expected, actual);
  }
  //  Risk = EarlyOnset : 5 keywords + age < 30 + sex == M.
  @Test
  void getAssessWithId_Male_ShouldReturnEarlyOnset() {

    // GIVEN
    patientHistory.setNotes(List.of(twoKeywordNote, threeKeywordNote));

    Assessment expected = new Assessment();
    expected.setAge(20);
    expected.setFamilyName(familyName);
    expected.setGivenName(givenName);
    message = AssessMessages.EARLY_ONSET;
    expected.setMessage(message);
    // WHEN
    when(patientHistoryRepoMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.of(patientHistory));
    when(patientInfoClientMock.getPatientByID(anyInt())).thenReturn(twentyYearOldMale);
    // THEN
    Assessment actual = assessService.getAssessWithId(patientId);
    assertEquals(expected, actual);
  }
  //  Risk = EarlyOnset : 7 keywords + age < 30 + sex == F.
  @Test
  void getAssessWithId_Female_ShouldReturnEarlyOnset() {

    // GIVEN
    patientHistory.setNotes(List.of(twoKeywordNote, threeKeywordNote, twoKeywordNote));

    Assessment expected = new Assessment();
    expected.setAge(20);
    expected.setFamilyName(familyName);
    expected.setGivenName(givenName);
    message = AssessMessages.EARLY_ONSET;
    expected.setMessage(message);
    // WHEN
    when(patientHistoryRepoMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.of(patientHistory));
    when(patientInfoClientMock.getPatientByID(anyInt())).thenReturn(twentyYearOldFemale);
    // THEN
    Assessment actual = assessService.getAssessWithId(patientId);
    assertEquals(expected, actual);
  }
  //  Risk = EarlyOnset : 8 <= keywords + age > 30.
  @Test
  void getAssessWithId_Both_ShouldReturnEarlyOnset() {

    // GIVEN
    patientHistory.setNotes(List.of(twoKeywordNote, threeKeywordNote, threeKeywordNote));

    Assessment expected = new Assessment();
    expected.setAge(50);
    expected.setFamilyName(familyName);
    expected.setGivenName(givenName);
    message = AssessMessages.EARLY_ONSET;
    expected.setMessage(message);
    // WHEN
    when(patientHistoryRepoMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.of(patientHistory));
    when(patientInfoClientMock.getPatientByID(anyInt())).thenReturn(fiftyYearOldMale);
    // THEN
    Assessment actual = assessService.getAssessWithId(patientId);
    assertEquals(expected, actual);
  }
}
