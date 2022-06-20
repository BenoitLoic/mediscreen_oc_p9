package com.mediscreen.patientinfo.repository;

import com.mediscreen.patientinfo.model.Patient;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** JPA repository for Patient entity. */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
  Optional<Patient> findByFamilyNameAndGivenName(String familyName, String givenName);
}
