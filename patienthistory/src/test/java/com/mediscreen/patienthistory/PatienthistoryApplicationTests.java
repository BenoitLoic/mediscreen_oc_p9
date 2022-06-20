package com.mediscreen.patienthistory;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mediscreen.patienthistory.model.History;
import com.mediscreen.patienthistory.model.Note;
import com.mediscreen.patienthistory.repository.PatientHistoryRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PatienthistoryApplicationTests {
  Logger logger = LoggerFactory.getLogger(PatienthistoryApplicationTests.class);

  @Autowired PatientHistoryRepository patientHistoryRepository;

  @Autowired MongoOperations mongoOperations;
  @Autowired MockMvc mockMvc;

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
            Query.query(Criteria.where("familyName").is("familyName10")), History.class);

    // WHEN

    // THEN
    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/patHistory/get")
                    .param("familyName", "familyName1")
                    .param("givenName", "givenName1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    ObjectMapper obm = new ObjectMapper();
    obm.registerModule(new JavaTimeModule());
    History actual = obm.readValue(mvcResult.getResponse().getContentAsString(), History.class);
    if (expected == null) {
      throw new AssertionError("TEST: Can't find patientHistory in DB.");
    }
    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getPatientId(), actual.getPatientId());
  }
}
