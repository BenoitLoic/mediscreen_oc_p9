package com.mediscreen.patientassessment.integrationTest;

import com.mediscreen.patientassessment.exception.BadArgumentException;
import com.mediscreen.patientassessment.exception.DataNotFoundException;
import com.mediscreen.patientassessment.feign.PatientHistoryClient;
import com.mediscreen.patientassessment.feign.PatientInfoClient;
import com.mediscreen.patientassessment.model.History;
import com.mediscreen.patientassessment.model.Note;
import com.mediscreen.patientassessment.model.PatientInfo;
import com.mediscreen.patientassessment.service.impl.AssessServiceImpl;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientAssessmentIT {

  private final String givenName = "givenNameTest";
  private final String familyName = "familyNameTest";
  private final int patientId = 2;
  private final PatientInfo twentyYearOldMale = new PatientInfo();
  private final PatientInfo twentyYearOldFemale = new PatientInfo();
  private final PatientInfo fiftyYearOldMale = new PatientInfo();
  private final Note noKeywordNote = new Note();
  private final Note twoKeywordNote = new Note();
  private final Note threeKeywordNote = new Note();
  private String message;
  private History patientHistory;

  @Autowired private MockMvc mockMvc;
  @MockBean private PatientInfoClient patientInfoClientMock;
  @MockBean private PatientHistoryClient patientHistoryClientMock;
  @InjectMocks private AssessServiceImpl assessService;

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

  @Test
  void getAssessWithIdValid_ShouldReturnNone() throws Exception {

    // GIVEN
    patientHistory.setNotes(List.of(noKeywordNote));
    // WHEN
    when(patientHistoryClientMock.getPatientHistoryById(anyInt())).thenReturn(patientHistory);
    when(patientInfoClientMock.getPatientById(anyInt())).thenReturn(fiftyYearOldMale);
    // THEN
    mockMvc
        .perform(post("/assess/id").param("patientId", String.valueOf(patientId)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(containsString("\"message\":\"None\"")));
  }

  @Test
  void getAssessWithIdValid_ShouldReturnBorderline() throws Exception {

    // GIVEN
    patientHistory.setNotes(List.of(twoKeywordNote));
    // WHEN
    when(patientHistoryClientMock.getPatientHistoryById(anyInt())).thenReturn(patientHistory);
    when(patientInfoClientMock.getPatientById(anyInt())).thenReturn(fiftyYearOldMale);
    // THEN
    mockMvc
        .perform(post("/assess/id").param("patientId", String.valueOf(patientId)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(containsString("\"message\":\"Borderline\"")));
  }

  @Test
  void getAssessWithIdValid_ShouldReturnInDanger() throws Exception {

    // GIVEN
    patientHistory.setNotes(List.of(threeKeywordNote));
    // WHEN
    when(patientHistoryClientMock.getPatientHistoryById(anyInt())).thenReturn(patientHistory);
    when(patientInfoClientMock.getPatientById(anyInt())).thenReturn(twentyYearOldMale);
    // THEN
    mockMvc
        .perform(post("/assess/id").param("patientId", String.valueOf(patientId)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(containsString("\"message\":\"In Danger\"")));
  }

  @Test
  void getAssessWithIdValid_ShouldReturnEarlyOnset() throws Exception {

    // GIVEN
    patientHistory.setNotes(List.of(twoKeywordNote, threeKeywordNote));
    // WHEN
    when(patientHistoryClientMock.getPatientHistoryById(anyInt())).thenReturn(patientHistory);
    when(patientInfoClientMock.getPatientById(anyInt())).thenReturn(twentyYearOldMale);
    // THEN
    mockMvc
        .perform(post("/assess/id").param("patientId", String.valueOf(patientId)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(containsString("\"message\":\"Early onset\"")));
  }

  @Test
  void getAssessWithIdInvalid_ShouldThrowBadArgumentException() throws Exception {

    // GIVEN

    // WHEN

    // THEN
    mockMvc
        .perform(post("/assess/id").param("patientId", "0"))
        .andExpect(status().isBadRequest())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof BadArgumentException));
  }

  @Test
  void getAssessWithId_WhenPatientDoesntExist_ShouldThrowDataNotFoundException() throws Exception {

    // GIVEN

    // WHEN
    doThrow(DataNotFoundException.class).when(patientInfoClientMock).getPatientById(anyInt());
    // THEN
    mockMvc
        .perform(post("/assess/id").param("patientId", String.valueOf(patientId)))
        .andExpect(status().isNotFound())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));
  }

  @Test
  void getAssessWithFamilyNameAndGivenNameValid() throws Exception {

    // GIVEN
    patientHistory.setNotes(List.of(noKeywordNote));
    // WHEN
    when(patientHistoryClientMock.getPatientHistoryByFamilyNameAndGivenName(
            anyString(), anyString()))
        .thenReturn((patientHistory));
    when(patientInfoClientMock.getPatientByFamilyNameAndGivenName(anyString(), anyString()))
        .thenReturn(twentyYearOldFemale);
    // THEN
    mockMvc
        .perform(post("/assess/name").param("familyName", familyName).param("givenName", givenName))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(containsString("\"message\":\"None\"")));
  }

  @Test
  void getAssessWithFamilyNameAndGivenNameInvalid_ShouldThrowBadArgumentException()
      throws Exception {

    // GIVEN
    String illegalArgument = "";
    // WHEN

    // THEN
    mockMvc
        .perform(
            post("/assess/name")
                .param("familyName", familyName)
                .param("givenName", illegalArgument))
        .andExpect(status().isBadRequest())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof BadArgumentException));
  }

  @Test
  void getAssessWithFamilyNameAndGivenName_WhenPatientDoesntExist_ShouldThrowDataNotFoundException()
      throws Exception {

    // GIVEN

    // WHEN
    doThrow(DataNotFoundException.class)
        .when(patientInfoClientMock)
        .getPatientByFamilyNameAndGivenName(anyString(), anyString());

    // THEN
    mockMvc
        .perform(post("/assess/name").param("familyName", familyName).param("givenName", givenName))
        .andExpect(status().isNotFound())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));
  }
}
