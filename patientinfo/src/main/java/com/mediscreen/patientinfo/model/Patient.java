package com.mediscreen.patientinfo.model;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Patient entity.
 * Represent the patient personal information.
 */
@Entity
@Table(name = "patient")
public class Patient {
  @Id private int id;

  @Column(name = "family_name")
  private String familyName;

  @Column(name = "given_name")
  private String givenName;

  @Column(name = "date_of_birth")
  private LocalDate birthDate;

  @Column(name = "sex")
  private String sex;

  @Column(name = "address")
  private String address;

  @Column(name = "phone")
  private String phone;

  public Patient() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate dateOfBirth) {
    this.birthDate = dateOfBirth;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Patient patient = (Patient) o;
    return id == patient.id
        && familyName.equals(patient.familyName)
        && givenName.equals(patient.givenName)
        && birthDate.equals(patient.birthDate)
        && sex.equals(patient.sex)
        && address.equals(patient.address)
        && phone.equals(patient.phone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, familyName, givenName, birthDate, sex, address, phone);
  }

  @Override
  public String toString() {
    return "Patient{"
        + "id="
        + id
        + ", familyName='"
        + familyName
        + '\''
        + ", givenName='"
        + givenName
        + '\''
        + ", dateOfBirth="
        + birthDate
        + ", sex='"
        + sex
        + '\''
        + ", address='"
        + address
        + '\''
        + ", phone='"
        + phone
        + '\''
        + '}';
  }
}
