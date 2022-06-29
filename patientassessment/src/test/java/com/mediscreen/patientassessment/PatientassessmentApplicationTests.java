package com.mediscreen.patientassessment;

import com.mediscreen.patientassessment.model.History;
import com.mediscreen.patientassessment.model.Note;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PatientassessmentApplicationTests {
  Logger logger = LoggerFactory.getLogger(this.getClass());
  @Autowired MongoOperations mongoOperations;

  @Test
  void populateHistoryDb() {

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
      Note note1 = new Note();
      Note note2 = new Note();
      note1.setText("premiere note");
      note2.setText("deuxieme note");
      history.setNotes(Arrays.asList(note1, note2));
      histories.add(history);
    }
    History hist = new History();
    hist.setFamilyName("Benoit");
    hist.setGivenName("Loic");
    hist.setPatientId(5);
    Note note = new Note();
    note.setText(
        "At vero eos et accusamus et iusto odio dignissimos "
            + "ducimus qui blanditiis praesentium Hémoglobine A1C voluptatum deleniti atque corrupti quos dolores "
            + "et quas molestias excepturi sint occaecati cupiditate non provident, similique "
            + "sunt in culpa qui Microalbumine officia deserunt Poids mollitia animi, "
            + "id est laborum et dolorum fuga.");
    note.setDate(LocalDateTime.now());
    hist.setNotes(List.of(note));
    histories.add(hist);
    logger.info("Populate DB.");
    mongoOperations.insertAll(histories);
  }

  @Test
  void clearDb() {
    logger.info("Drop history test collection");
    mongoOperations.dropCollection(History.class);
  }
}
