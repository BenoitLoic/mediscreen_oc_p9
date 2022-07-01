package com.mediscreen.patientassessment.constant;

import java.util.List;

/** Contains the english keywords that trigger the diabetes assessment. */
public class TriggerKeywordsEng {
  public static final String HEMOGLOBINE_A1C = "hemoglobin a1c ";
  public static final String MICROALBUMINE = "microalbumin ";
  public static final String TAILLE = "height";
  public static final String POIDS = "weight";
  public static final String FUMEUR = "smoker";
  public static final String ANORMAL = "abnormal ";
  public static final String CHOLESTEROL = "cholesterol ";
  public static final String VERTIGE = "dizziness";
  public static final String RECHUTE = "relapse";
  public static final String REACTION = "reaction";
  public static final String ANTICORPS = "antibodies";
  public static final List<String> KEYWORDS_ENG =
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
}
