package com.mediscreen.patienthistory.model.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/** Dto for Update Note feature. */
public class UpdateNoteDto {
  @NotNull private Integer patientId;
  @NotNull private LocalDateTime date;
  @NotNull private String text;

  public Integer getPatientId() {
    return patientId;
  }

  public void setPatientId(Integer patientId) {
    this.patientId = patientId;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateNoteDto that = (UpdateNoteDto) o;
    return Objects.equals(patientId, that.patientId)
        && Objects.equals(date, that.date)
        && Objects.equals(text, that.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(patientId, date, text);
  }

  @Override
  public String toString() {
    return "UpdateNoteDto{"
        + "patientId="
        + patientId
        + ", date="
        + date
        + ", text='"
        + text
        + '\''
        + '}';
  }
}
