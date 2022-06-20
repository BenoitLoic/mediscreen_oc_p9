package com.mediscreen.patienthistory.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.mediscreen.patienthistory.model.History;

public interface PatientHistoryRepository extends MongoRepository<History,String> {
    Optional<History> findHistoryByFamilyNameAndGivenName(String familyName, String givenName);
}
