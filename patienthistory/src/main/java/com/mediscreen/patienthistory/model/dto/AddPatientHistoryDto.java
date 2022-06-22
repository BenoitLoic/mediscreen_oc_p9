package com.mediscreen.patienthistory.model.dto;

import com.mediscreen.patienthistory.model.Note;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Dto for patient history creation.
 */
public class AddPatientHistoryDto {
  @NotNull private Integer patientId;
  @NotBlank private String familyName;
  @NotBlank private String givenName;
  private final Collection<Note> notes = new ArrayList<>();

  public AddPatientHistoryDto() {
  }

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

  public Collection<Note> getNotes() {
    return notes;
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
        && Objects.equals(notes, that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(patientId, familyName, givenName, notes);
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
        + ", notes="
        + notes
        + '}';
  }
}
