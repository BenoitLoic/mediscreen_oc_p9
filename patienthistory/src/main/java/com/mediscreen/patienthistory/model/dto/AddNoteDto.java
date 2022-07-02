package com.mediscreen.patienthistory.model.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * Dto for addNote feature.
 */
public class AddNoteDto {
  @NotNull private Integer patientId;
  @NotNull private String textNote;

  public Integer getPatientId() {
    return patientId;
  }

  public void setPatientId(Integer patientId) {
    this.patientId = patientId;
  }

  public String getTextNote() {
    return textNote;
  }

  public void setTextNote(String textNote) {
    this.textNote = textNote;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddNoteDto that = (AddNoteDto) o;
    return Objects.equals(patientId, that.patientId) && Objects.equals(textNote, that.textNote);
  }

  @Override
  public int hashCode() {
    return Objects.hash(patientId, textNote);
  }

  @Override
  public String toString() {
    return "AddNoteDto{" + "patientId=" + patientId + ", textNote='" + textNote + '\'' + '}';
  }
}
