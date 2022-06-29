package com.mediscreen.patientassessment.repository;

import com.mediscreen.patientassessment.model.History;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientHistoryRepository extends MongoRepository<History, String> {

  Optional<History> findHistoryByPatientId(int patientId);

  Optional<History> findHistoryByFamilyNameAndGivenName(String familyName, String givenName);
}
