package com.mediscreen.patienthistory.model.dto;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** Dto for patient history creation. */
public class AddPatientHistoryDto {
  @NotNull private Integer patientId;
  @NotBlank private String familyName;
  @NotBlank private String givenName;
  @NotNull private String textNote;

  public AddPatientHistoryDto() {}

  public AddPatientHistoryDto(Integer patientId, String familyName, String givenName) {
    this.patientId = patientId;
    this.familyName = familyName;
    this.givenName = givenName;
  }

  public Integer getPatientId() {
    return patientId;
  }

  public String getFamilyName() {
    return familyName;
  }

  public String getGivenName() {
    return givenName;
  }

  public String getTextNote() {
    return textNote;
  }

  public void setTextNote(String textNote) {
    this.textNote = textNote;
  }

  @Override
  public String toString() {
    return "AddPatientHistoryDto{"
        + "patientId="
        + patientId
        + ", familyName='"
        + familyName
        + '\''
        + ", givenName='"
        + givenName
        + '\''
        + ", textNote='"
        + textNote
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddPatientHistoryDto that = (AddPatientHistoryDto) o;
    return Objects.equals(patientId, that.patientId)
        && Objects.equals(familyName, that.familyName)
        && Objects.equals(givenName, that.givenName)
        && Objects.equals(textNote, that.textNote);
  }

  @Override
  public int hashCode() {
    return Objects.hash(patientId, familyName, givenName, textNote);
  }
}
