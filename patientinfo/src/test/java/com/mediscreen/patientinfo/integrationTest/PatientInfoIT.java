package com.mediscreen.patientinfo.integrationTest;

import com.mediscreen.patientinfo.exception.BadArgumentException;
import com.mediscreen.patientinfo.exception.DataAlreadyExistException;
import com.mediscreen.patientinfo.exception.DataNotFoundException;
import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.model.dto.CreatePatientDto;
import com.mediscreen.patientinfo.model.dto.UpdatePatientDto;
import com.mediscreen.patientinfo.repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PatientInfoIT {

  @Container
  public static MySQLContainer mySQLContainer =
      new MySQLContainer<>("mysql:8.0.29")
          .withUsername("test")
          .withPassword("test")
          .withDatabaseName("MediscreenSql")
          .withInitScript("scripts/sqlCreate.sql");
  private final String familyName = "familyNameTest1";
  @Autowired private PatientRepository patientRepository;
  @Autowired private MockMvc mockMvc;

  @DynamicPropertySource
  static void dataBaseProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
    registry.add("spring.datasource.username", mySQLContainer::getUsername);
    registry.add("spring.datasource.password", mySQLContainer::getPassword);
  }

  @AfterAll
  static void afterAll() {
    mySQLContainer.stop();
  }

  @Test
  void getPatientByFamilyNameAndLastNameValid() throws Exception {
    // GIVEN

    // WHEN

    // THEN
    String givenName = "givenNameTest1";
    mockMvc
        .perform(get("/patient/get").param("family", familyName).param("given", givenName))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(containsString("\"id\":1")))
        .andExpect(content().string(containsString("\"familyName\":\"familyNameTest1\"")));
  }

  @Test
  void getPatientByFamilyNameAndLastNameInvalid_ShouldThrowBadArgumentException() throws Exception {
    // GIVEN

    // WHEN

    // THEN
    String invalid = "";
    mockMvc
        .perform(get("/patient/get").param("family", familyName).param("given", invalid))
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                Assertions.assertTrue(
                    result.getResolvedException() instanceof BadArgumentException));
  }

  @Test
  void getPatientByFamilyNameAndLastName_WhenDoesNotExist_ShouldThrowDataNotFoundException()
      throws Exception {
    // GIVEN

    // WHEN

    // THEN
    String unknown = "woops";
    mockMvc
        .perform(get("/patient/get").param("family", familyName).param("given", unknown))
        .andExpect(status().isNotFound())
        .andExpect(
            result ->
                Assertions.assertTrue(
                    result.getResolvedException() instanceof DataNotFoundException));
  }

  @Test
  void getPatientByIdValid() throws Exception {
    // GIVEN

    // WHEN

    // THEN
    mockMvc
        .perform(get("/patient/id/get").param("id", "2"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(containsString("\"id\":2")))
        .andExpect(content().string(containsString("\"familyName\":\"familyNameTest2\"")));
  }

  @Test
  void getPatientByIdInvalid_ShouldThrowBadArgumentException() throws Exception {
    // GIVEN

    // WHEN

    // THEN
    mockMvc
        .perform(get("/patient/id/get").param("id", String.valueOf(0)))
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                Assertions.assertTrue(
                    result.getResolvedException() instanceof BadArgumentException));
  }

  @Test
  void getPatientById_WhenDoesNotExist_ShouldThrowDataNotFoundException() throws Exception {
    // GIVEN

    // WHEN

    // THEN
    mockMvc
        .perform(get("/patient/id/get").param("id", String.valueOf(99)))
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
    patient.setGivenName("updatedGivenName");
    patient.setFamilyName("updatedFamilyName");
    patient.setSex("M");
    patient.setId(3);
    patient.setBirthDate(LocalDate.of(1999, 3, 20));

    String json = objectMapper.writeValueAsString(patient);
    // WHEN

    // THEN
    mockMvc
        .perform(put("/patient/update").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isAccepted())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(containsString("\"id\":3")))
        .andExpect(content().string(containsString("\"familyName\":\"updatedFamilyName\"")))
        .andExpect(content().string(containsString("\"givenName\":\"updatedGivenName\"")));
  }

  @Test
  void updatePatientInvalid_ShouldThrowBadArgumentException() throws Exception {
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
  void updatePatient_WhenDoesNotExist_ShouldThrowDataNotFoundException() throws Exception {
    // GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    UpdatePatientDto patient = new UpdatePatientDto();
    patient.setId(99);
    patient.setGivenName("nametest");
    patient.setFamilyName("familynametest");
    patient.setAddress("addresstest");
    patient.setPhone("phonetest");
    patient.setSex("M");
    patient.setBirthDate(LocalDate.of(1999, 3, 20));

    String json = objectMapper.writeValueAsString(patient);
    // WHEN

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

    // THEN
    mockMvc
        .perform(post("/patient/add").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    Assertions.assertEquals(
        5,
        patientRepository
            .findByFamilyNameAndGivenName("familynametest", "nametest")
            .map(Patient::getId)
            .orElse(0));
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
  void createPatient_WhenAlreadyExist_ShouldThrowDataAlreadyExistException() throws Exception {
    // GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    CreatePatientDto patient = new CreatePatientDto();
    patient.setGivenName("givenNameTest4");
    patient.setFamilyName("familyNameTest4");
    patient.setAddress("addresstest");
    patient.setPhone("phonetest");
    patient.setSex("M");
    patient.setBirthDate(LocalDate.of(1999, 3, 20));
    String json = objectMapper.writeValueAsString(patient);
    // WHEN

    // THEN
    mockMvc
        .perform(post("/patient/add").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andExpect(
            result ->
                Assertions.assertTrue(
                    result.getResolvedException() instanceof DataAlreadyExistException));
  }
}
