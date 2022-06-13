package com.mediscreen.patientinfo.controller.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.mediscreen.patientinfo.exception.BadArgumentException;
import com.mediscreen.patientinfo.exception.DataNotFoundException;
import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.service.PatientService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientControllerImpl.class)
class PatientControllerTest {

  @Autowired MockMvc mockMvc;

  @MockBean PatientService patientServiceMock;

  @Test
  void getPatientValid() throws Exception {

    // GIVEN
    String familyName = "testName";
    String givenName = "givenNameTest";
    // WHEN
    Mockito.when(patientServiceMock.getPatient(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(new Patient());
    // THEN
    mockMvc
        .perform(get("/patient/get").param("family", familyName).param("given", givenName))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void getPatientInvalid_ShouldThrowBadArgumentException() throws Exception {

    // GIVEN
    String familyName = "testName";
    String givenName = "";
    // WHEN
    Mockito.when(patientServiceMock.getPatient(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(new Patient());
    // THEN
    mockMvc
        .perform(get("/patient/get").param("family", familyName).param("given", givenName))
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                Assertions.assertTrue(
                    result.getResolvedException() instanceof BadArgumentException));
  }

  @Test
  void getPatient_ShouldThrowDataNotFoundException() throws Exception {

    // GIVEN
    String familyName = "testName";
    String givenName = "givenNameTest";
    // WHEN
    Mockito.doThrow(DataNotFoundException.class)
        .when(patientServiceMock)
        .getPatient(Mockito.anyString(), Mockito.anyString());
    // THEN
    mockMvc
        .perform(get("/patient/get").param("family", familyName).param("given", givenName))
        .andExpect(status().isNotFound())
        .andExpect(
            result ->
                Assertions.assertTrue(
                    result.getResolvedException() instanceof DataNotFoundException));
  }
}
