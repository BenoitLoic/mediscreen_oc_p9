package com.mediscreen.patientinfo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mediscreen.patientinfo.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Optional<Patient> findByFamilyNameAndGivenName(String familyName, String givenName);
}
