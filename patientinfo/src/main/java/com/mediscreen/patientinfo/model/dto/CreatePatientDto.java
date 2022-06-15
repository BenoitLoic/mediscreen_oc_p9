package com.mediscreen.patientinfo.model.dto;

import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** Dto for patient creation feature. */
public class CreatePatientDto {

  @NotBlank private String familyName;
  @NotBlank private String givenName;
  @NotNull private LocalDate birthDate;
  @NotBlank private String sex;
  @NotBlank private String address;
  @NotBlank private String phone;

  public CreatePatientDto() {}

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

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
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
        CreatePatientDto that = (CreatePatientDto) o;
        return familyName.equals(that.familyName) && givenName.equals(that.givenName) && birthDate.equals(that.birthDate) && sex.equals(that.sex) && address.equals(that.address) && phone.equals(that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(familyName, givenName, birthDate, sex, address, phone);
    }

    @Override
    public String toString() {
        return "CreatePatientDto{" +
                "familyName='" + familyName + '\'' +
                ", givenName='" + givenName + '\'' +
                ", birthDate=" + birthDate +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
