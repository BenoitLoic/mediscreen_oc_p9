package com.mediscreen.patientinfo.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.mediscreen.patientinfo.exception.DataAlreadyExistException;
import com.mediscreen.patientinfo.exception.DataNotFoundException;
import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.model.dto.CreatePatientDto;
import com.mediscreen.patientinfo.model.dto.UpdatePatientDto;
import com.mediscreen.patientinfo.repository.PatientRepository;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
    when(patientRepositoryMock.findByFamilyNameAndGivenName(anyString(), anyString()))
        .thenReturn(Optional.of(patient));
    // THEN
    Patient result = patientService.getPatient(familyName, givenName);
    assertEquals(result.getFamilyName(), familyName);
  }

  @Test
  void getPatient_ShouldThrowDataNotFoundException() {

    // GIVEN
    String familyName = "Baggins";
    String givenName = "Frodo";
    // WHEN
    when(patientRepositoryMock.findByFamilyNameAndGivenName(anyString(), anyString()))
        .thenReturn(Optional.empty());
    // THEN
    assertThrows(
        DataNotFoundException.class, () -> patientService.getPatient(familyName, givenName));
  }

  @Test
  void updatePatient() {

    // GIVEN
    Patient patient = new Patient();
    patient.setId(5);
    patient.setGivenName("nametest");
    patient.setFamilyName("familynametest");
    patient.setPhone("phoneTest");
    patient.setSex("M");
    patient.setBirthDate(LocalDate.of(1999, 3, 20));

    UpdatePatientDto updatePatientDto = new UpdatePatientDto();
    updatePatientDto.setId(5);
    updatePatientDto.setGivenName("nametest");
    updatePatientDto.setFamilyName("familynametest");
    updatePatientDto.setAddress("update_address_test");
    updatePatientDto.setPhone("phonetest");
    updatePatientDto.setSex("M");
    // WHEN
    when(patientRepositoryMock.findById(anyInt())).thenReturn(Optional.of(patient));
    when(patientRepositoryMock.save(any(Patient.class))).thenReturn(new Patient());
    // THEN
    Patient result = patientService.updatePatient(updatePatientDto);
    assertEquals(result.getAddress(), updatePatientDto.getAddress());
  }

  @Test
  void updatePatient_ShouldThrowDataNotFoundException() {

    // GIVEN
    UpdatePatientDto updatePatientDto = new UpdatePatientDto();
    updatePatientDto.setId(5);
    updatePatientDto.setGivenName("nametest");
    updatePatientDto.setFamilyName("familynametest");
    updatePatientDto.setAddress("update_address_test");
    updatePatientDto.setPhone("phonetest");
    updatePatientDto.setSex("M");
    // WHEN
    when(patientRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());
    // THEN
    assertThrows(DataNotFoundException.class, () -> patientService.updatePatient(updatePatientDto));
  }

  @Test
  void updatePatient_ShouldNotUpdateAnyFields() {

    // GIVEN
    Patient patient = new Patient();
    patient.setId(5);
    patient.setGivenName("nametest");
    patient.setFamilyName("familynametest");
    patient.setPhone("phoneTest");
    patient.setSex("M");
    patient.setBirthDate(LocalDate.of(1999, 3, 20));

    UpdatePatientDto updatePatientDto = new UpdatePatientDto();
    updatePatientDto.setId(5);
    updatePatientDto.setFamilyName("testlastname");
    updatePatientDto.setGivenName("testfirstname");
    // WHEN
    when(patientRepositoryMock.findById(anyInt())).thenReturn(Optional.of(patient));

    when(patientRepositoryMock.save(Mockito.any())).thenReturn(new Patient());
    // THEN
    Patient result = patientService.updatePatient(updatePatientDto);
    assertEquals(result, patient);
  }

  @Test
  void createPatient() {
    // GIVEN
    CreatePatientDto patient = new CreatePatientDto();
    patient.setGivenName("nametest");
    patient.setFamilyName("validFamilyName");
    patient.setAddress("addresstest");
    patient.setPhone("phonetest");
    patient.setSex("M");
    patient.setBirthDate(LocalDate.of(1999, 3, 20));

    Patient expectedPatient = new Patient();
    expectedPatient.setGivenName("nametest");
    expectedPatient.setFamilyName("validFamilyName");
    expectedPatient.setAddress("addresstest");
    expectedPatient.setPhone("phonetest");
    expectedPatient.setSex("M");
    expectedPatient.setBirthDate(LocalDate.of(1999, 3, 20));
    // WHEN
    when(patientRepositoryMock.findByFamilyNameAndGivenName(anyString(), anyString()))
        .thenReturn(Optional.empty())
        .thenReturn(Optional.of(new Patient()));
    when(patientRepositoryMock.save(Mockito.any())).thenReturn(new Patient());
    // THEN
    patientService.createPatient(patient);
    verify(patientRepositoryMock, times(1)).save(expectedPatient);
  }

  @Test
  void createPatient_WhenPatientAlreadyExist_ShouldThrowDataAlreadyExistException() {
    // GIVEN
    CreatePatientDto patient = new CreatePatientDto();
    patient.setGivenName("nametest");
    patient.setFamilyName("validFamilyName");
    patient.setAddress("addresstest");
    patient.setPhone("phonetest");
    patient.setSex("M");
    patient.setBirthDate(LocalDate.of(1999, 3, 20));

    Patient patientFromRepo = new Patient();
    patientFromRepo.setGivenName("nametest");
    patientFromRepo.setFamilyName("validFamilyName");
    patientFromRepo.setId(2);
    // WHEN
    when(patientRepositoryMock.findByFamilyNameAndGivenName(anyString(), anyString()))
        .thenReturn(Optional.of(patientFromRepo));
    // THEN}
    assertThrows(DataAlreadyExistException.class, () -> patientService.createPatient(patient));
  }
}
