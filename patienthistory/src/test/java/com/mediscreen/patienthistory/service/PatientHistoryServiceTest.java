package com.mediscreen.patienthistory.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.mediscreen.patienthistory.exception.DataNotFoundException;
import com.mediscreen.patienthistory.model.History;
import com.mediscreen.patienthistory.repository.PatientHistoryRepository;
import com.mediscreen.patienthistory.service.impl.PatientHistoryServiceImpl;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientHistoryServiceTest {

  @Mock private PatientHistoryRepository patientHistoryRepositoryMock;

  @InjectMocks private PatientHistoryServiceImpl patientHistoryService;

  @Test
  void getPatientHistory() {
    // GIVEN
    String familyName = "fName";
    String givenName = "gName";
    History history = new History();
    history.setGivenName(givenName);
    // WHEN
    when(patientHistoryRepositoryMock.findHistoryByFamilyNameAndGivenName(anyString(), anyString()))
        .thenReturn(Optional.of(history));
    // THEN
    History actual = patientHistoryService.getPatientHistory(familyName, givenName);
    verify(patientHistoryRepositoryMock, times(1))
        .findHistoryByFamilyNameAndGivenName(familyName, givenName);
    assertEquals(givenName, actual.getGivenName());
  }

  @Test
  void getPatientHistory_WhenPatientDoesntExist_ShouldThrowDataNotFoundException() {
    // GIVEN
    String familyName = "fName";
    String givenName = "gName";
    // WHEN
    when(patientHistoryRepositoryMock.findHistoryByFamilyNameAndGivenName(anyString(), anyString()))
        .thenReturn(Optional.empty());
    // THEN
    assertThrows(
        DataNotFoundException.class,
        () -> patientHistoryService.getPatientHistory(familyName, givenName));
  }
}
