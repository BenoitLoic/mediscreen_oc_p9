package com.mediscreen.patientinfo.controller.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mediscreen.patientinfo.exception.BadArgumentException;
import com.mediscreen.patientinfo.exception.DataNotFoundException;
import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.model.dto.UpdatePatientDto;
import com.mediscreen.patientinfo.service.PatientService;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientControllerImpl.class)
class PatientControllerTest {

  @Autowired private MockMvc mockMvc;

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

  @Test
  void updatePatientValid() throws Exception {
    // GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    UpdatePatientDto patient = new UpdatePatientDto();
    patient.setGivenName("nametest");
    patient.setFamilyName("familynametest");
    patient.setSex("M");
    patient.setId(2);
    patient.setBirthDate(LocalDate.of(1999, 3, 20));

    String json = objectMapper.writeValueAsString(patient);

    // WHEN
    Mockito.when(patientServiceMock.updatePatient(Mockito.any(UpdatePatientDto.class)))
        .thenReturn(new Patient());
    // THEN
    mockMvc
        .perform(put("/patient/update").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isAccepted())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void updatePatientInvalid_ShouldThrowIllegalArgumentException() throws Exception {
    // GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    UpdatePatientDto invalidPatient = new UpdatePatientDto();
    invalidPatient.setGivenName("");
    invalidPatient.setFamilyName("familynametest");
    invalidPatient.setAddress("addresstest");
    invalidPatient.setPhone("phonetest");
    invalidPatient.setSex("M");
    String json = objectMapper.writeValueAsString(invalidPatient);
    // WHEN

    // THEN
    mockMvc
        .perform(put("/patient/update").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                Assertions.assertTrue(
                    result.getResolvedException() instanceof BadArgumentException));
  }

  @Test
  void updatePatient_ShouldThrowDataNotFoundException() throws Exception {
    // GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    UpdatePatientDto patient = new UpdatePatientDto();
    patient.setId(2);
    patient.setGivenName("nametest");
    patient.setFamilyName("familynametest");
    patient.setAddress("addresstest");
    patient.setPhone("phonetest");
    patient.setSex("M");
    patient.setBirthDate(LocalDate.of(1999, 3, 20));

    String json = objectMapper.writeValueAsString(patient);
    // WHEN
    Mockito.doThrow(DataNotFoundException.class)
        .when(patientServiceMock)
        .updatePatient(Mockito.any(UpdatePatientDto.class));
    // THEN
    mockMvc
        .perform(put("/patient/update").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(
            result ->
                Assertions.assertTrue(
                    result.getResolvedException() instanceof DataNotFoundException));
  }
}
