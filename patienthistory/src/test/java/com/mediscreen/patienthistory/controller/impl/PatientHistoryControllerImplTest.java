package com.mediscreen.patienthistory.controller.impl;

import com.mediscreen.patienthistory.exception.BadArgumentException;
import com.mediscreen.patienthistory.exception.DataNotFoundException;
import com.mediscreen.patienthistory.model.History;
import com.mediscreen.patienthistory.model.Note;
import com.mediscreen.patienthistory.model.dto.UpdateHistoryDto;
import com.mediscreen.patienthistory.service.PatientHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientHistoryControllerImpl.class)
class PatientHistoryControllerImplTest {

  private final String givenName = "givenNameTest";
  private final String familyName = "familyNameTest";
  private final String invalidFamilyName = "";
  @MockBean PatientHistoryService patientHistoryServiceMock;
  @Autowired private MockMvc mockMvc;

  @Test
  void getPatientHistoryValid() throws Exception {

    // GIVEN

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

  @Test
  void updatePatientHistoryValid() throws Exception {
    // GIVEN
    Note note =
        new Note("Get started with Spring 5 and Spring Boot 2, through the Learn Spring course:");
    UpdateHistoryDto updateHistoryDto = new UpdateHistoryDto();
    updateHistoryDto.setPatientId(1);
    updateHistoryDto.setFamilyName(familyName);
    updateHistoryDto.setGivenName(givenName);
    updateHistoryDto.setNotes(List.of(note));
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    var json = objectMapper.writeValueAsString(updateHistoryDto);
    // WHEN
    when(patientHistoryServiceMock.updatePatientHistory(any())).thenReturn(new History());
    // THEN
    mockMvc
        .perform(put("/patHistory/update").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isAccepted())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void updatePatientHistoryInvalid_ShouldThrowBadArgumentException() throws Exception {
    // GIVEN
    Note note =
        new Note("Get started with Spring 5 and Spring Boot 2, through the Learn Spring course:");
    UpdateHistoryDto updateHistoryDto = new UpdateHistoryDto();
    updateHistoryDto.setPatientId(1);
    updateHistoryDto.setFamilyName(invalidFamilyName);
    updateHistoryDto.setGivenName(givenName);
    updateHistoryDto.setNotes(List.of(note));
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    var json = objectMapper.writeValueAsString(updateHistoryDto);
    // WHEN

    // THEN
    mockMvc
        .perform(put("/patHistory/update").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof BadArgumentException));
  }

  @Test
  void updatePatientHistory_WhenHistoryDoesntExist_ShouldThrowDataNotFoundException()
      throws Exception {
    // GIVEN
    Note note =
        new Note("Get started with Spring 5 and Spring Boot 2, through the Learn Spring course:");
    UpdateHistoryDto updateHistoryDto = new UpdateHistoryDto();
    updateHistoryDto.setPatientId(64646);
    updateHistoryDto.setFamilyName(familyName);
    updateHistoryDto.setGivenName(givenName);
    updateHistoryDto.setNotes(List.of(note));
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    var json = objectMapper.writeValueAsString(updateHistoryDto);
    // WHEN
    doThrow(DataNotFoundException.class)
        .when(patientHistoryServiceMock)
        .updatePatientHistory(any(UpdateHistoryDto.class));
    // THEN
    mockMvc
        .perform(put("/patHistory/update").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));
  }
}
