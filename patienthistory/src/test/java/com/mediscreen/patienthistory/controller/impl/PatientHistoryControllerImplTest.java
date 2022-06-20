package com.mediscreen.patienthistory.controller.impl;

import com.mediscreen.patienthistory.exception.BadArgumentException;
import com.mediscreen.patienthistory.exception.DataNotFoundException;
import com.mediscreen.patienthistory.model.History;
import com.mediscreen.patienthistory.service.PatientHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientHistoryControllerImpl.class)
class PatientHistoryControllerImplTest {

  @MockBean PatientHistoryService patientHistoryServiceMock;
  @Autowired private MockMvc mockMvc;

  @Test
  void getPatientHistoryValid() throws Exception {

    // GIVEN
    String givenName = "givenNameTest";
    String familyName = "familyNameTest";
    // WHEN
    when(patientHistoryServiceMock.getPatientHistory(anyString(), anyString()))
        .thenReturn(new History());
    // THEN
    mockMvc
        .perform(
            get("/patHistory/get").param("givenName", givenName).param("familyName", familyName))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void getPatientHistoryInvalid_ShouldThrowBadArgumentException() throws Exception {

    // GIVEN
    String givenName = "givenNameTest";
    String invalidFamilyName = "";
    // WHEN

    // THEN
    mockMvc
        .perform(
            get("/patHistory/get")
                .param("givenName", givenName)
                .param("familyName", invalidFamilyName))
        .andExpect(status().isBadRequest())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof BadArgumentException));
  }

  @Test
  void getPatientHistory_WhenPatientDoesNotExist_ShouldThrowDataNotFoundException()
      throws Exception {

    // GIVEN
    String givenName = "givenNameTest";
    String familyName = "familyNameTest";
    // WHEN
    doThrow(DataNotFoundException.class)
        .when(patientHistoryServiceMock)
        .getPatientHistory(anyString(), anyString());
    // THEN
    mockMvc
        .perform(
            get("/patHistory/get").param("givenName", givenName).param("familyName", familyName))
        .andExpect(status().isNotFound())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));
  }
}
