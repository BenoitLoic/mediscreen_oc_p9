package com.mediscreen.patientassessment.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Model for Note object.
 */
public class Note {
  private LocalDateTime date;
  private String text;

  public Note() {}

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Note note = (Note) o;
    return Objects.equals(date, note.date) && Objects.equals(text, note.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, text);
  }

  @Override
  public String toString() {
    return "Note{" + "date=" + date + ", text='" + text + '\'' + '}';
  }
}
