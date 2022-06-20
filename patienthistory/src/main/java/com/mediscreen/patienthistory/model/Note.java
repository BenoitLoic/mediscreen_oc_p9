package com.mediscreen.patienthistory.model;

import java.time.LocalDateTime;

/**
 * Model for Note object.
 * Represent the practitioner's note.
 */
public class Note {

  private LocalDateTime date;
  private String text;

  public Note() {}

  public Note(String text) {
    this.text = text;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
