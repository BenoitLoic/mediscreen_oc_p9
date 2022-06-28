package com.mediscreen.patientassessment.constant;

import java.util.stream.Stream;

public enum TriggerKeywords {
  HEMOGLOBINE_A1C("Hémoglobine A1C"),
  MICROALBUMINE("Microalbumine"),
  TAILLE("Taille"),
  POIDS("Poids"),
  FUMEUR("Fumeur"),
  ANORMAL("Anormal"),
  CHOLESTEROL("Cholestérol"),
  VERTIGE("Vertige"),
  RECHUTE("Rechute"),
  REACTION("Réaction"),
  ANTICORPS("Anticorps");

  private String keyword;

  TriggerKeywords(String keyword) {
    this.keyword = keyword;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public static Stream<TriggerKeywords> stream() {
    return Stream.of(TriggerKeywords.values());
  }
}
