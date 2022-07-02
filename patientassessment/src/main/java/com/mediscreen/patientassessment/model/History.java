package com.mediscreen.patientassessment.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Model for patient History object.
 */
public class History {
  private String id;
  private Integer patientId;
  private String familyName;
  private String givenName;
  private Collection<Note> notes = new ArrayList<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

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
    History that = (History) o;
    return Objects.equals(id, that.id)
        && Objects.equals(patientId, that.patientId)
        && Objects.equals(familyName, that.familyName)
        && Objects.equals(givenName, that.givenName)
        && Objects.equals(notes, that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, patientId, familyName, givenName, notes);
  }

  @Override
  public String toString() {
    return "PatientHistory{"
        + "id='"
        + id
        + '\''
        + ", patientId="
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
