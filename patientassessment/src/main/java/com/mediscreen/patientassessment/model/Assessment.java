package com.mediscreen.patientassessment.model;

import java.util.Objects;

/**
 * Assess model.
 */
public class Assessment {

  private String familyName;
  private String givenName;
  private int age;
  private String message;

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

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Assessment that = (Assessment) o;
    return age == that.age
        && Objects.equals(familyName, that.familyName)
        && Objects.equals(givenName, that.givenName)
        && Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(familyName, givenName, age, message);
  }

  @Override
  public String toString() {
    return "Assessement{"
        + "familyName='"
        + familyName
        + '\''
        + ", givenName='"
        + givenName
        + '\''
        + ", age="
        + age
        + ", message='"
        + message
        + '\''
        + '}';
  }
}
