package com.mediscreen.patienthistory.model.dto;

import com.mediscreen.patienthistory.model.Note;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** Dto for updateHistory feature. Contains javax validation annotations. */
public class UpdateHistoryDto {

  @NotNull private Integer patientId;
  @NotBlank private String familyName;
  @NotBlank private String givenName;
  private Collection<Note> notes = new ArrayList<>();

  public Integer getPatientId() {
    return patientId;
  }

  public void setPatientId(Integer patientId) {
    this.patientId = patientId;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public Collection<Note> getNotes() {
    return notes;
  }

  public void setNotes(Collection<Note> notes) {
    this.notes = notes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateHistoryDto that = (UpdateHistoryDto) o;
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
    return "UpdateHistoryDto{"
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
