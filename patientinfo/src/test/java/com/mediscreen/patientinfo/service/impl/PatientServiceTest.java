package com.mediscreen.patientinfo.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.mediscreen.patientinfo.exception.DBConnectionException;
import com.mediscreen.patientinfo.exception.DataNotFoundException;
import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.repository.PatientRepository;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

  @Mock PatientRepository patientRepositoryMock;
  @InjectMocks PatientServiceImpl patientService;

  @Test
  void getPatient() {

    // GIVEN
    String familyName = "Baggins";
    String givenName = "Frodo";
    Patient patient = new Patient();
    patient.setFamilyName(familyName);
    patient.setGivenName(givenName);
    // WHEN
    Mockito.when(
                    patientRepositoryMock.findByFamilyNameAndGivenName(
                            Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Optional.of(patient));
    // THEN
    Patient result = patientService.getPatient(familyName, givenName);
    Assertions.assertEquals(result.getFamilyName(), familyName);
  }

  @Test
  void getPatient_ShouldThrowDataNotFoundException() {

    // GIVEN
    String familyName = "Baggins";
    String givenName = "Frodo";
    // WHEN
    Mockito.when(
            patientRepositoryMock.findByFamilyNameAndGivenName(
                Mockito.anyString(), Mockito.anyString()))
        .thenReturn(Optional.empty());
    // THEN
    Assertions.assertThrows(
        DataNotFoundException.class, () -> patientService.getPatient(familyName, givenName));
  }


}
