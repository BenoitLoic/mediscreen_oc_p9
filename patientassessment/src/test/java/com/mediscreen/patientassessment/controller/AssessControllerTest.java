package com.mediscreen.patientassessment.controller;

import com.mediscreen.patientassessment.controller.impl.AssessControllerImpl;
import com.mediscreen.patientassessment.exception.BadArgumentException;
import com.mediscreen.patientassessment.exception.DataNotFoundException;
import com.mediscreen.patientassessment.model.Assessment;
import com.mediscreen.patientassessment.service.AssessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AssessControllerImpl.class)
class AssessControllerTest {

  private final String familyName = "familyNameTest";
  private final String givenName = "givenNameTest";
  private final int patientId = 5;
  @Autowired private MockMvc mockMvc;
  @MockBean private AssessService assessService;

  @Test
  void getAssessWithIdValid() throws Exception {

    // GIVEN

    // WHEN
    when(assessService.getAssessWithId(anyInt())).thenReturn(new Assessment());
    // THEN
    mockMvc
        .perform(post("/assess/id").param("patientId", String.valueOf(patientId)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
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
    doThrow(DataNotFoundException.class).when(assessService).getAssessWithId(anyInt());
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

    // WHEN
    when(assessService.getAssessWithFamilyNameAndGivenName(anyString(), anyString()))
        .thenReturn(new Assessment());
    // THEN
    mockMvc
        .perform(post("/assess/name").param("familyName", familyName).param("givenName", givenName))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
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
        .when(assessService)
        .getAssessWithFamilyNameAndGivenName(anyString(), anyString());

    // THEN
    mockMvc
        .perform(post("/assess/name").param("familyName", familyName).param("givenName", givenName))
        .andExpect(status().isNotFound())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));
  }
}
