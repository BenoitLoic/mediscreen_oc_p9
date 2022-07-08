package com.mediscreen.patienthistory.service;

import com.mediscreen.patienthistory.exception.DataAlreadyExistException;
import com.mediscreen.patienthistory.exception.DataNotFoundException;
import com.mediscreen.patienthistory.model.History;
import com.mediscreen.patienthistory.model.Note;
import com.mediscreen.patienthistory.model.dto.AddNoteDto;
import com.mediscreen.patienthistory.model.dto.AddPatientHistoryDto;
import com.mediscreen.patienthistory.model.dto.UpdateHistoryDto;
import com.mediscreen.patienthistory.model.dto.UpdateNoteDto;
import com.mediscreen.patienthistory.repository.PatientHistoryRepository;
import com.mediscreen.patienthistory.service.impl.PatientHistoryServiceImpl;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Repeat;
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
    when(patientHistoryRepositoryMock.findHistoryByFamilyNameIgnoreCaseAndGivenNameIgnoreCase(
            anyString(), anyString()))
        .thenReturn(Optional.of(history));
    // THEN
    History actual =
        patientHistoryService.getPatientHistoryByFamilyNameAndGivenName(familyName, givenName);
    verify(patientHistoryRepositoryMock, times(1))
        .findHistoryByFamilyNameIgnoreCaseAndGivenNameIgnoreCase(familyName, givenName);
    assertEquals(givenName, actual.getGivenName());
  }

  @Test
  void getPatientHistory_WhenPatientDoesntExist_ShouldThrowDataNotFoundException() {
    // GIVEN

    // WHEN
    when(patientHistoryRepositoryMock.findHistoryByFamilyNameIgnoreCaseAndGivenNameIgnoreCase(
            anyString(), anyString()))
        .thenReturn(Optional.empty());
    // THEN
    assertThrows(
        DataNotFoundException.class,
        () ->
            patientHistoryService.getPatientHistoryByFamilyNameAndGivenName(familyName, givenName));
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
    LocalDateTime now = LocalDateTime.of(2020, 03, 01, 20, 00, 10);
    try (MockedStatic<LocalDateTime> mock = Mockito.mockStatic(LocalDateTime.class)) {
      mock.when(LocalDateTime::now).thenReturn(now);

      note.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
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
    LocalDateTime now = LocalDateTime.of(2020,03,01,20,00,10);
    try(MockedStatic<LocalDateTime> mock = Mockito.mockStatic(LocalDateTime.class)) {
      mock.when(LocalDateTime::now).thenReturn(now);

      Note note1 = new Note("azerty qsdfg wxcv");
    Note note2 = new Note("azerty qdsfsdgsdf  zéez tt");
    note1.setDate(LocalDateTime.now());
    note2.setDate(LocalDateTime.now());
    History savedHistory = new History();
    savedHistory.setGivenName(givenName);
    savedHistory.setFamilyName(familyName);
    savedHistory.setPatientId(50);
    savedHistory.setNotes(List.of(note1, note2));

    AddNoteDto addNote = new AddNoteDto();
    addNote.setTextNote("test");
    addNote.setPatientId(50);

    Note newNote = new Note();
    newNote.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
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
  }}

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

  @Test
  void updatePatientHistoryNote() {
    // GIVEN
    LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    UpdateNoteDto noteToUpdate = new UpdateNoteDto();
    noteToUpdate.setPatientId(1);
    noteToUpdate.setText("updatedText");
    noteToUpdate.setDate(date);

    Note note1 = new Note("azerty qsdfg wxcv");
    Note note2 = new Note("azerty qdsfsdgsdf  zéez tt");
    note1.setDate(date);
    note2.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusDays(1));
    History savedHistory = new History();
    savedHistory.setGivenName(givenName);
    savedHistory.setFamilyName(familyName);
    savedHistory.setPatientId(1);
    savedHistory.setNotes(List.of(note1, note2));
    History expected = new History();
    expected.setGivenName(givenName);
    expected.setFamilyName(familyName);
    expected.setPatientId(1);
    Note newNote = new Note();
    newNote.setDate(noteToUpdate.getDate());
    newNote.setText(noteToUpdate.getText());
    expected.setNotes(List.of(newNote, note2));

    // WHEN
    when(patientHistoryRepositoryMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.of(savedHistory));
    when(patientHistoryRepositoryMock.save(any())).thenReturn(new History());
    // THEN
    patientHistoryService.updatePatientHistoryNote(noteToUpdate);
    verify(patientHistoryRepositoryMock, times(1)).findHistoryByPatientId(1);
    verify(patientHistoryRepositoryMock, times(1)).save(expected);
  }

  @Test
  void updatePatientHistoryNote_WhenHistoryDoesntExist_ShouldThrowDataNotFoundException() {
    // GIVEN
    UpdateNoteDto updateNoteDto = new UpdateNoteDto();
    updateNoteDto.setPatientId(1);
    // WHEN
    when(patientHistoryRepositoryMock.findHistoryByPatientId(anyInt()))
        .thenReturn(Optional.empty());
    // THEN
    assertThrows(
        DataNotFoundException.class,
        () -> patientHistoryService.updatePatientHistoryNote(updateNoteDto));
  }
}
