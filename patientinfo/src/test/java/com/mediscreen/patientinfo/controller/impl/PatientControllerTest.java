package com.mediscreen.patientinfo.controller.impl;

import com.mediscreen.patientinfo.exception.BadArgumentException;
import com.mediscreen.patientinfo.exception.DataAlreadyExistException;
import com.mediscreen.patientinfo.exception.DataNotFoundException;
import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.model.dto.CreatePatientDto;
import com.mediscreen.patientinfo.model.dto.UpdatePatientDto;
import com.mediscreen.patientinfo.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientControllerImpl.class)
class PatientControllerTest {

  @MockBean PatientService patientServiceMock;
  @Autowired private MockMvc mockMvc;

  @Test
  void getPatientValid() throws Exception {

    // GIVEN
    String familyName = "testName";
    String givenName = "givenNameTest";
    // WHEN
    when(patientServiceMock.getPatientByFamilyNameAndGivenName(anyString(), anyString()))
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
    when(patientServiceMock.getPatientByFamilyNameAndGivenName(anyString(), anyString()))
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
    doThrow(DataNotFoundException.class)
        .when(patientServiceMock)
        .getPatientByFamilyNameAndGivenName(anyString(), anyString());
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
    when(patientServiceMock.updatePatient(any(UpdatePatientDto.class))).thenReturn(new Patient());
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
    doThrow(DataNotFoundException.class)
        .when(patientServiceMock)
        .updatePatient(any(UpdatePatientDto.class));
    // THEN
    mockMvc
        .perform(put("/patient/update").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(
            result ->
                Assertions.assertTrue(
                    result.getResolvedException() instanceof DataNotFoundException));
  }

  @Test
  void createPatientValid() throws Exception {
    // GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    CreatePatientDto patient = new CreatePatientDto();
    patient.setGivenName("nametest");
    patient.setFamilyName("familynametest");
    patient.setAddress("addresstest");
    patient.setPhone("phonetest");
    patient.setSex("M");
    patient.setBirthDate(LocalDate.of(1999, 3, 20));
    String json = objectMapper.writeValueAsString(patient);

    // WHEN
    when(patientServiceMock.createPatient(any())).thenReturn(new Patient());
    // THEN
    mockMvc
        .perform(post("/patient/add").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void createPatientInvalid_ShouldThrowBadArgumentException() throws Exception {
    // GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    CreatePatientDto patient = new CreatePatientDto();
    patient.setGivenName("nametest");
    patient.setFamilyName("");
    patient.setAddress("addresstest");
    patient.setPhone("phonetest");
    patient.setSex("M");
    patient.setBirthDate(LocalDate.of(1999, 3, 20));
    String json = objectMapper.writeValueAsString(patient);
    // WHEN

    // THEN
    mockMvc
        .perform(post("/patient/add").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                Assertions.assertTrue(
                    result.getResolvedException() instanceof BadArgumentException));
  }

  @Test
  void createPatient_ShouldThrowDataAlreadyExistException() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    CreatePatientDto patient = new CreatePatientDto();
    patient.setGivenName("nametest");
    patient.setFamilyName("familynametest");
    patient.setAddress("addresstest");
    patient.setPhone("phonetest");
    patient.setSex("M");
    patient.setBirthDate(LocalDate.of(1999, 3, 20));
    String json = objectMapper.writeValueAsString(patient);

    // WHEN
    doThrow(DataAlreadyExistException.class).when(patientServiceMock).createPatient(any());
    // THEN
    mockMvc
        .perform(post("/patient/add").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andExpect(
            result ->
                Assertions.assertTrue(
                    result.getResolvedException() instanceof DataAlreadyExistException));
  }

  @Test
  void getPatientByIdValid() throws Exception {

    // GIVEN
    int patientId = 2;
    // WHEN
    when(patientServiceMock.getPatientById(anyInt())).thenReturn(new Patient());
    // THEN
    mockMvc
        .perform(get("/patient/id/get").param("id", String.valueOf(patientId)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void getPatientByIdInvalid_ShouldThrowBadArgumentException() throws Exception {

    // GIVEN
    int patientId = 0;

    // WHEN
    when(patientServiceMock.getPatientById(anyInt())).thenReturn(new Patient());
    // THEN
    mockMvc
        .perform(get("/patient/id/get").param("id", String.valueOf(patientId)))
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                Assertions.assertTrue(
                    result.getResolvedException() instanceof BadArgumentException));
  }

  @Test
  void getPatientById_ShouldThrowDataNotFoundException() throws Exception {

    // GIVEN
    int patientId = 2;

    // WHEN
    doThrow(DataNotFoundException.class).when(patientServiceMock).getPatientById(anyInt());
    // THEN
    mockMvc
        .perform(get("/patient/id/get").param("id", String.valueOf(patientId)))
        .andExpect(status().isNotFound())
        .andExpect(
            result ->
                Assertions.assertTrue(
                    result.getResolvedException() instanceof DataNotFoundException));
  }
}
