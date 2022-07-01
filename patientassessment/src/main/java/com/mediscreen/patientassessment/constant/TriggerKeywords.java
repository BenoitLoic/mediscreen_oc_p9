package com.mediscreen.patientassessment.constant;

import java.util.List;
import java.util.stream.Stream;

/** Contains the keywords that trigger the diabetes assessment. */
public class TriggerKeywords {
  public static final String HEMOGLOBINE_A1C = "hémoglobine a1c";
  public static final String MICROALBUMINE = "microalbumine";
  public static final String TAILLE = "taille";
  public static final String POIDS = "poids";
  public static final String FUMEUR = "fumeur";
  public static final String ANORMAL = "anormal";
  public static final String CHOLESTEROL = "cholestérol";
  public static final String VERTIGE = "vertige";
  public static final String RECHUTE = "rechute";
  public static final String REACTION = "réaction";
  public static final String ANTICORPS = "anticorps";
  public static final List<String> KEYWORDS_FR =
      List.of(
          HEMOGLOBINE_A1C,
          MICROALBUMINE,
          TAILLE,
          POIDS,
          FUMEUR,
          ANORMAL,
          CHOLESTEROL,
          VERTIGE,
          RECHUTE,
          REACTION,
          ANTICORPS);
  public static final List<String> KEYWORDS =
      Stream.concat(KEYWORDS_FR.stream(), TriggerKeywordsEng.KEYWORDS_ENG.stream()).toList();
}
