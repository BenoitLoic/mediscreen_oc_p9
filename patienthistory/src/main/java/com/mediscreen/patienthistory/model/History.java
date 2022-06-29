package com.mediscreen.patienthistory.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/** Patient History entity. */
@Document
public class History {

  @Id private String id;
  @Indexed(unique = true) private Integer patientId;
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
    History history = (History) o;
    return Objects.equals(id, history.id)
        && Objects.equals(patientId, history.patientId)
        && Objects.equals(familyName, history.familyName)
        && Objects.equals(givenName, history.givenName)
        && Objects.equals(notes, history.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, patientId, familyName, givenName, notes);
  }

  @Override
  public String toString() {
    return "History{"
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
