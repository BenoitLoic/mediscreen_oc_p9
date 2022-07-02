package com.mediscreen.patienthistory.controller.impl;

import com.mediscreen.patienthistory.controller.PatientHistoryController;
import com.mediscreen.patienthistory.exception.BadArgumentException;
import com.mediscreen.patienthistory.model.History;
import com.mediscreen.patienthistory.model.dto.AddNoteDto;
import com.mediscreen.patienthistory.model.dto.AddPatientHistoryDto;
import com.mediscreen.patienthistory.model.dto.UpdateHistoryDto;
import com.mediscreen.patienthistory.model.dto.UpdateNoteDto;
import com.mediscreen.patienthistory.service.PatientHistoryService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Implementation for Patient History Rest Controller. */
@RestController
@CrossOrigin
@RequestMapping("/patHistory")
public class PatientHistoryControllerImpl implements PatientHistoryController {

  Logger logger = LoggerFactory.getLogger(PatientHistoryControllerImpl.class);

  @Autowired private PatientHistoryService patientHistoryService;

  /**
   * Get all Notes for the given patient. Can throw BadArgumentException if argument is blank or
   * null.
   *
   * @param familyName the patient's lastname
   * @param givenName the patient's firstname
   * @return collection with all saved notes
   */
  @Override
  @GetMapping("/name/get")
  @ResponseStatus(HttpStatus.OK)
  public History getPatientHistoryByFamilyNameAndGivenName(
      @RequestParam String familyName, @RequestParam String givenName) {

    if (familyName.isBlank() || givenName.isBlank()) {
      logger.warn(
          "Error, invalid argument for familyName:'"
              + familyName
              + "' - givenName:'"
              + givenName
              + "'. error: can't be null or blank.");
      throw new BadArgumentException("KO, invalid argument.");
    }
    logger.trace("Getting history for patient: " + givenName + " - " + familyName + ".");

    return patientHistoryService.getPatientHistoryByFamilyNameAndGivenName(familyName, givenName);
  }

  /**
   * Get all Notes for the given patient. Can throw BadArgumentException if argument is blank or
   * null.
   *
   * @param patientId the patient id
   * @return collection with all saved notes
   */
  @Override
  @GetMapping("/id/get")
  @ResponseStatus(HttpStatus.OK)
  public History getPatientHistoryByPatientId(@RequestParam int patientId) {

    if (patientId <= 0) {
      logger.warn(
          "Error, invalid argument for patientId:'"
              + patientId
              + "'. error: can't be null or blank.");
      throw new BadArgumentException("KO, invalid argument.");
    }
    logger.trace("Getting history for patientId: " + patientId);

    return patientHistoryService.getPatientHistoryById(patientId);
  }

  /**
   * Update the patient history based on the patient ID. Can throw BadArgumentException if either
   * patientId, givenName or familyName is null or blank.
   *
   * @param updateHistoryDto the history to update
   * @return the updated history
   */
  @Override
  @ResponseStatus(HttpStatus.ACCEPTED)
  @PutMapping("/update")
  public History updatePatientHistory(
      @Valid @RequestBody UpdateHistoryDto updateHistoryDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      List<String> array =
          bindingResult.getFieldErrors().stream().map(FieldError::getField).toList();
      logger.warn("Error, invalid argument:" + array);
      throw new BadArgumentException("KO, invalid argument.");
    }
    logger.trace("Update history for patient with id: " + updateHistoryDto.getPatientId());
    return patientHistoryService.updatePatientHistory(updateHistoryDto);
  }

  /**
   * Create a new patient history.Can throw BadArgumentException if either * patientId, givenName or
   * familyName is null or blank. Can throw DataAlreadyExistException if patientId is already
   * present in db.
   *
   * @param addPatientHistoryDto the history to create
   * @return the history created
   */
  @Override
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/add")
  public History createPatientHistory(
      @Valid @RequestBody AddPatientHistoryDto addPatientHistoryDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      List<String> array =
          bindingResult.getFieldErrors().stream().map(FieldError::getField).toList();
      logger.warn("Error, invalid argument:" + array);
      throw new BadArgumentException("KO, invalid argument.");
    }
    logger.trace("Create history for patient with id: " + addPatientHistoryDto.getPatientId());
    return patientHistoryService.createPatientHistory(addPatientHistoryDto);
  }

  /**
   * Create a new Note. Can throw BadArgumentException if either patientId or text is null.
   *
   * @param addNote the note to create
   * @return the patient history
   */
  @Override
  @PostMapping("/note/add")
  @ResponseStatus(HttpStatus.CREATED)
  public History addNote(@Valid @RequestBody AddNoteDto addNote, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      List<String> array =
          bindingResult.getFieldErrors().stream().map(FieldError::getField).toList();
      logger.warn("Error, invalid argument:" + array);
      throw new BadArgumentException("KO, invalid argument.");
    }
    logger.trace("Create new note for patient with id: " + addNote.getPatientId());
    return patientHistoryService.createPatientHistoryNote(addNote);
  }

  /**
   * Update the note from an existing patient history.
   *
   * @param updateNoteDto the note to update.
   * @return the updated history
   */
  @Override
  @PutMapping("/note/update")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public History updateNote(
      @Valid @RequestBody UpdateNoteDto updateNoteDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      List<String> array =
          bindingResult.getFieldErrors().stream().map(FieldError::getField).toList();
      logger.warn("Error, invalid argument:" + array);
      throw new BadArgumentException("KO, invalid argument.");
    }
    logger.trace("Update note for patient with id: " + updateNoteDto.getPatientId());
    return patientHistoryService.updatePatientHistoryNote(updateNoteDto);
  }
}
