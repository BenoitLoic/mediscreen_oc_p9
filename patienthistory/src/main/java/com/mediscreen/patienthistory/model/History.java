package com.mediscreen.patienthistory.model;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Patient History entity.
 */
@Document
public class History {

  @Id private String id;
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
}
