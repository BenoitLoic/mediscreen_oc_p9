package com.mediscreen.patienthistory.service;

import com.mediscreen.patienthistory.exception.DataAlreadyExistException;
import com.mediscreen.patienthistory.exception.DataNotFoundException;
import com.mediscreen.patienthistory.model.History;
import com.mediscreen.patienthistory.model.Note;
import com.mediscreen.patienthistory.model.dto.AddNoteDto;
import com.mediscreen.patienthistory.model.dto.AddPatientHistoryDto;
import com.mediscreen.patienthistory.model.dto.UpdateHistoryDto;
import com.mediscreen.patienthistory.repository.PatientHistoryRepository;
import com.mediscreen.patienthistory.service.impl.PatientHistoryServiceImpl;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientHistoryServiceTest {
  private final String familyName = "fName";
  private final String givenName = "gName";
  @Mock private PatientHistoryRepository patientHistoryRepositoryMock;

  @InjectMocks private PatientHistoryServiceImpl patientHistoryService;

  @Test
  void getPatientHistory() {
    // GIVEN
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

    // WHEN
    when(patientHistoryRepositoryMock.findHistoryByFamilyNameAndGivenName(anyString(), anyString()))
        .thenReturn(Optional.empty());
    // THEN
    assertThrows(
        DataNotFoundException.class,
        () -> patientHistoryService.getPatientHistory(familyName, givenName));
  }

  @Test
  void updatePatientHistory_ShouldUpdateFamilyName() {
    // GIVEN
    Note note1 = new Note("azerty qsdfg wxcv");
    Note note2 = new Note("azerty qdsfsdgsdf  zéez tt");
    note2.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minus(1, ChronoUnit.DAYS));

    History history = new History();
    history.setGivenName(givenName);
    history.setFamilyName(familyName);
    history.setPatientId(50);
    history.setNotes(List.of(note1, note2));

    UpdateHistoryDto updateHistoryDto = new UpdateHistoryDto();
    updateHistoryDto.setGivenName(history.getGivenName());
    updateHistoryDto.setFamilyName("Updated lastname");
    updateHistoryDto.setPatientId(history.getPatientId());
    // WHEN
    when(patientHistoryRepositoryMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.of(history));
    when(patientHistoryRepositoryMock.save(any(History.class))).thenReturn(new History());
    // THEN
    patientHistoryService.updatePatientHistory(updateHistoryDto);
    verify(patientHistoryRepositoryMock, times(1)).findHistoryByPatientId(50);
    history.setFamilyName("Updated lastname");
    verify(patientHistoryRepositoryMock, times(1)).save(history);
  }

  @Test
  void updatePatientHistory_ShouldUpdateNote1() {
    // GIVEN
    Note note1 = new Note("azerty qsdfg wxcv");
    Note note2 = new Note("azerty qdsfsdgsdf  zéez tt");
    note1.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    note2.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minus(1, ChronoUnit.DAYS));
    History history = new History();
    history.setGivenName(givenName);
    history.setFamilyName(familyName);
    history.setPatientId(50);
    history.setNotes(List.of(note1, note2));

    UpdateHistoryDto updateHistoryDto = new UpdateHistoryDto();
    updateHistoryDto.setGivenName(history.getGivenName());
    updateHistoryDto.setFamilyName(history.getFamilyName());
    updateHistoryDto.setPatientId(history.getPatientId());
    Note updateNote = new Note();
    updateNote.setDate(note1.getDate());
    updateNote.setText("updated Note!");
    updateHistoryDto.setNotes(List.of(updateNote));
    History expectedSave = new History();
    expectedSave.setGivenName(givenName);
    expectedSave.setFamilyName(familyName);
    expectedSave.setPatientId(50);
    expectedSave.setNotes(List.of(updateNote, note2));
    // WHEN
    when(patientHistoryRepositoryMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.of(history));
    when(patientHistoryRepositoryMock.save(any(History.class))).thenReturn(new History());
    // THEN
    patientHistoryService.updatePatientHistory(updateHistoryDto);
    verify(patientHistoryRepositoryMock, times(1)).findHistoryByPatientId(50);

    verify(patientHistoryRepositoryMock, times(1)).save(expectedSave);
  }

  @Test
  void updatePatientHistory_ShouldAdd2Notes() {
    // GIVEN
    Note note1 = new Note("azerty qsdfg wxcv");
    Note note2 = new Note("azerty qdsfsdgsdf  zéez tt");
    note1.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    note2.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minus(1, ChronoUnit.DAYS));
    History history = new History();
    history.setGivenName(givenName);
    history.setFamilyName(familyName);
    history.setPatientId(50);

    UpdateHistoryDto updateHistoryDto = new UpdateHistoryDto();
    updateHistoryDto.setGivenName(history.getGivenName());
    updateHistoryDto.setFamilyName(history.getFamilyName());
    updateHistoryDto.setPatientId(history.getPatientId());
    updateHistoryDto.setNotes(List.of(note1, note2));
    History expectedSave = new History();
    expectedSave.setGivenName(givenName);
    expectedSave.setFamilyName(familyName);
    expectedSave.setPatientId(50);
    expectedSave.setNotes(List.of(note1, note2));
    // WHEN
    when(patientHistoryRepositoryMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.of(history));
    when(patientHistoryRepositoryMock.save(any(History.class))).thenReturn(new History());
    // THEN
    patientHistoryService.updatePatientHistory(updateHistoryDto);
    verify(patientHistoryRepositoryMock, times(1)).findHistoryByPatientId(50);

    verify(patientHistoryRepositoryMock, times(1)).save(expectedSave);
  }

  @Test
  void updatePatientHistory_WhenHistoryDoesntExist_ShouldThrowDataNotFoundException() {
    // GIVEN
    UpdateHistoryDto updateHistoryDto = new UpdateHistoryDto();
    updateHistoryDto.setGivenName("givenName");
    updateHistoryDto.setFamilyName("Updated lastname");
    updateHistoryDto.setPatientId(5);
    // WHEN
    when(patientHistoryRepositoryMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.empty());
    // THEN
    assertThrows(
        DataNotFoundException.class,
        () -> patientHistoryService.updatePatientHistory(updateHistoryDto));
  }

  @Test
  void createPatientHistory() {
    // GIVEN
    AddPatientHistoryDto addHistory =
        new AddPatientHistoryDto(5, "createFamilyNameTest", "createGivenNameTest");
    Note note = new Note();
    note.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    note.setText("test text");
    addHistory.setTextNote("test text");
    History history = new History();
    history.setPatientId(addHistory.getPatientId());
    history.setGivenName(addHistory.getGivenName());
    history.setFamilyName(addHistory.getFamilyName());
    history.setNotes(List.of(note));
    // WHEN
    when(patientHistoryRepositoryMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.empty());
    when(patientHistoryRepositoryMock.save(any(History.class))).thenReturn(new History());
    // THEN
    patientHistoryService.createPatientHistory(addHistory);
    verify(patientHistoryRepositoryMock, times(1))
        .findHistoryByPatientId(addHistory.getPatientId());
    verify(patientHistoryRepositoryMock, times(1)).save(history);
  }

  @Test
  void createPatientHistory_WhenHistoryAlreadyExist_ShouldThrowDataAlreadyExistException() {
    // GIVEN
    AddPatientHistoryDto addHistory =
        new AddPatientHistoryDto(5, "createFamilyNameTest", "createGivenNameTest");

    addHistory.setTextNote("test text");

    // WHEN
    when(patientHistoryRepositoryMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.of(new History()));
    // THEN
    assertThrows(
        DataAlreadyExistException.class,
        () -> patientHistoryService.createPatientHistory(addHistory));
  }

  @Test
  void createPatientHistoryNote() {
    // GIVEN
    Note note1 = new Note("azerty qsdfg wxcv");
    Note note2 = new Note("azerty qdsfsdgsdf  zéez tt");
    note1.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    note2.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    History savedHistory = new History();
    savedHistory.setGivenName(givenName);
    savedHistory.setFamilyName(familyName);
    savedHistory.setPatientId(50);
    savedHistory.setNotes(List.of(note1, note2));

    AddNoteDto addNote = new AddNoteDto();
    addNote.setTextNote("test");
    addNote.setPatientId(50);

    Note newNote = new Note();
    newNote.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    newNote.setText("test");
    History expected = new History();
    expected.setGivenName(givenName);
    expected.setFamilyName(familyName);
    expected.setPatientId(50);
    expected.setNotes(List.of(note1, note2, newNote));

    // WHEN
    when(patientHistoryRepositoryMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.of(savedHistory));
    // THEN
    patientHistoryService.createPatientHistoryNote(addNote);
    verify(patientHistoryRepositoryMock, times(1)).save(expected);
  }

  @Test
  void createPatientHistoryNote_WhenHistoryDoesntExist_ShouldThrowDataNotFoundException() {
    // GIVEN
    AddNoteDto addNote = new AddNoteDto();
    addNote.setTextNote("test");
    addNote.setPatientId(1);

    // WHEN
    when(patientHistoryRepositoryMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.empty());
    // THEN
    assertThrows(
        DataNotFoundException.class, () -> patientHistoryService.createPatientHistoryNote(addNote));
  }
}
