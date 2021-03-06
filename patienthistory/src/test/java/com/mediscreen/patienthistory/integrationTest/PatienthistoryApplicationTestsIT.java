package com.mediscreen.patienthistory.integrationTest;

import com.mediscreen.patienthistory.PatienthistoryApplication;
import com.mediscreen.patienthistory.exception.BadArgumentException;
import com.mediscreen.patienthistory.exception.DataAlreadyExistException;
import com.mediscreen.patienthistory.exception.DataNotFoundException;
import com.mediscreen.patienthistory.model.History;
import com.mediscreen.patienthistory.model.Note;
import com.mediscreen.patienthistory.model.dto.AddPatientHistoryDto;
import com.mediscreen.patienthistory.model.dto.UpdateHistoryDto;
import com.mediscreen.patienthistory.repository.PatientHistoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(classes = PatienthistoryApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PatienthistoryApplicationTestsIT {
  @Container
  private static final MongoDBContainer mongoDBContainer =
      new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
  Logger logger = LoggerFactory.getLogger(PatienthistoryApplicationTestsIT.class);
  @Autowired PatientHistoryRepository patientHistoryRepository;
  @Autowired MongoOperations mongoOperations;
  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;

  @DynamicPropertySource
  static void mongoDbProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @BeforeEach
  void setUp() {

    if (!mongoOperations.collectionExists(History.class)) {
      logger.info("Create collection history test");
      mongoOperations.createCollection(History.class);
    }

    Collection<History> histories = new ArrayList<>();
    for (int i = 1; i < 4; i++) {
      History history = new History();
      history.setPatientId(i);
      history.setFamilyName("familyName" + i);
      history.setGivenName("givenName" + i);
      history.setNotes(Arrays.asList(new Note("premiere note"), new Note("deuxieme note")));
      histories.add(history);
    }
    logger.info("Populate DB.");
    mongoOperations.insertAll(histories);
  }

  @AfterEach
  void clearDb() {
    logger.info("Drop history test collection");
    mongoOperations.dropCollection(History.class);
  }

  @Test
  void readPatientHistory() throws Exception {
    // GIVEN
    History expected =
        mongoOperations.findOne(
            Query.query(Criteria.where("familyName").is("familyName1")), History.class);

    // WHEN

    // THEN
    MvcResult mvcResult =
        mockMvc
            .perform(
                get("/patHistory/name/get")
                    .param("familyName", "familyName1")
                    .param("givenName", "givenName1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    History actual =
        objectMapper.readValue(mvcResult.getResponse().getContentAsString(), History.class);
    if (expected == null) {
      throw new AssertionError("TEST: Can't find patientHistory in DB.");
    }
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getPatientId(), actual.getPatientId());
  }

  @Test
  void updatePatientHistory() throws Exception {
    // GIVEN

    History savedPatient = new History();
    savedPatient.setPatientId(10);
    savedPatient.setGivenName("givenNameTest");
    savedPatient.setFamilyName("familyNameTest");
    Note note = new Note("premiere note");
    note.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    savedPatient.setNotes(List.of(note));
    mongoOperations.insert(savedPatient);

    History patient =
        mongoOperations.findOne(Query.query(Criteria.where("patientId").is(10)), History.class);
    assert patient != null;

    UpdateHistoryDto updateHistoryDto = new UpdateHistoryDto();
    updateHistoryDto.setPatientId(savedPatient.getPatientId());
    updateHistoryDto.setFamilyName(savedPatient.getFamilyName());
    updateHistoryDto.setGivenName("updated Given Name");

    String json = objectMapper.writeValueAsString(updateHistoryDto);
    // WHEN

    // THEN
    mockMvc
        .perform(put("/patHistory/update").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isAccepted());
    History updatedPatient =
        mongoOperations.findOne(Query.query(Criteria.where("patientId").is(10)), History.class);

    assert updatedPatient != null;

    assertEquals(updatedPatient.getNotes().size(), 1);
    //noinspection OptionalGetWithoutIsPresent
    assertEquals(
        updateHistoryDto.getGivenName(), updatedPatient.getGivenName());
  }

  @Test
  void updatePatientHistory_WhenHistoryDoesntExist_ShouldThrowDataNotFoundException()
      throws Exception {
    // GIVEN

    Note updateNote = new Note("updated text!");
    UpdateHistoryDto updateHistoryDto = new UpdateHistoryDto();
    updateHistoryDto.setPatientId(15);
    updateHistoryDto.setGivenName("givenName2");
    updateHistoryDto.setFamilyName("familyName2");
    updateHistoryDto.setNotes(List.of(updateNote));
    var json = objectMapper.writeValueAsString(updateHistoryDto);
    // WHEN

    // THEN
    mockMvc
        .perform(put("/patHistory/update").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof DataNotFoundException));
  }

  @Test
  void updatePatientHistoryInvalid_ShouldThrowBadArgumentException() throws Exception {
    // GIVEN

    Note updateNote = new Note();
    updateNote.setText("updated text!");
    updateNote.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    UpdateHistoryDto dto = new UpdateHistoryDto();
    dto.setPatientId(1);
    dto.setFamilyName("");
    dto.setGivenName("givenNameTest3");
    dto.setNotes(List.of(updateNote));
    var json = objectMapper.writeValueAsString(dto);
    // WHEN

    // THEN
    mockMvc
        .perform(put("/patHistory/update").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof BadArgumentException));
  }

  @Test
  void createPatientHistoryValid() throws Exception {
    // GIVEN
    AddPatientHistoryDto addHistory =
        new AddPatientHistoryDto(5, "createFamilyNameTest", "createGivenNameTest");
    addHistory.setTextNote("test text");
    String json = objectMapper.writeValueAsString(addHistory);
    // WHEN

    // THEN
    mockMvc
        .perform(post("/patHistory/add").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
    History patient =
        mongoOperations.findOne(Query.query(Criteria.where("patientId").is(5)), History.class);
    assertNotNull(patient);
    assertNotNull(patient.getId());
  }

  @Test
  void createPatientHistoryInvalid_ShouldThrowBadArgumentException() throws Exception {
    // GIVEN
    AddPatientHistoryDto addHistory = new AddPatientHistoryDto(5, "", "createGivenNameTest");
    addHistory.setTextNote("test text");
    String json = objectMapper.writeValueAsString(addHistory);
    // WHEN

    // THEN
    mockMvc
        .perform(post("/patHistory/add").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof BadArgumentException));
  }

  @Test
  void createPatientHistory_WhenHistoryAlreadyExist_ShouldThrowDataAlreadyExistException()
      throws Exception {
    // GIVEN
    AddPatientHistoryDto addHistory =
        new AddPatientHistoryDto(1, "createFamilyNameTest", "createGivenNameTest");
    addHistory.setTextNote("test text");
    String json = objectMapper.writeValueAsString(addHistory);

    // WHEN

    // THEN
    mockMvc
        .perform(post("/patHistory/add").content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andExpect(
            result ->
                assertTrue(result.getResolvedException() instanceof DataAlreadyExistException));
  }
}
