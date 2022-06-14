package com.mediscreen.patientinfo.model.dto;

import java.util.Objects;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Dto for patient update feature.
 */
public class UpdatePatientDto {

    @Min(1)
    private int id;
    @NotBlank
    private String familyName;
    @NotBlank
    private String givenName;
    private LocalDate birthDate;
    private String sex;
    private String address;
    private String phone;

    public UpdatePatientDto() {
    }

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
        UpdatePatientDto that = (UpdatePatientDto) o;
        return id == that.id && Objects.equals(familyName, that.familyName) && Objects.equals(givenName, that.givenName) && Objects.equals(birthDate, that.birthDate) && Objects.equals(sex, that.sex) && Objects.equals(address, that.address) && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, familyName, givenName, birthDate, sex, address, phone);
    }

    @Override
    public String toString() {
        return "UpdatePatientDto{" +
                "id=" + id +
                ", familyName='" + familyName + '\'' +
                ", givenName='" + givenName + '\'' +
                ", birthDate=" + birthDate +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
