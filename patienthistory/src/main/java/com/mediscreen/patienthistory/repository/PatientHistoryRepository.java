package com.mediscreen.patienthistory.repository;

import com.mediscreen.patienthistory.model.History;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/** JPA repository for History Document. */
public interface PatientHistoryRepository extends MongoRepository<History, String> {
  Optional<History> findHistoryByFamilyNameAndGivenName(String familyName, String givenName);
}
