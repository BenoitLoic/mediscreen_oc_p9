package com.mediscreen.patientinfo.service.impl;

import com.mediscreen.patientinfo.exception.DataAlreadyExistException;
import com.mediscreen.patientinfo.exception.DataNotFoundException;
import com.mediscreen.patientinfo.model.Patient;
import com.mediscreen.patientinfo.model.dto.CreatePatientDto;
import com.mediscreen.patientinfo.model.dto.UpdatePatientDto;
import com.mediscreen.patientinfo.repository.PatientRepository;
import com.mediscreen.patientinfo.service.PatientService;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for patient service.
 *
 * <p>contains method used for CRUD on patient entity.
 */
@Service
public class PatientServiceImpl implements PatientService {

  Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);
  @Autowired private PatientRepository patientRepository;

  /**
   * Gets the properties to be ignored.
   *
   * @param source the source object to ignore null properties.
   * @return String[] that contain the source object's null fields
   */
  private static String[] getNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<>();
    for (PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());
      //  The judgment here can be modified according to the demand
      if (srcValue == null || srcValue.equals("")) {
        emptyNames.add(pd.getName());
      }
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

  /**
   * Copy the field from the source object to the target object by ignoring null fields.
   *
   * @param src the source object
   * @param target the target object
   */
  public static void myCopyProperties(Object src, Object target) {
    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
  }

  /**
   * Retrieve the Patient information from repository based on its lastname and firstname.
   *
   * @param familyName the patient lastname
   * @param givenName the patient firstname
   * @return the patient information
   */
  @Override
  public Patient getPatient(String familyName, String givenName) {
    Optional<Patient> patient =
        patientRepository.findByFamilyNameAndGivenName(familyName, givenName);
    if (patient.isEmpty()) {
      logger.warn(
          "Error, patient with familyName: "
              + familyName
              + " and givenName: "
              + givenName
              + " doesn't exist.");
      throw new DataNotFoundException("KO, patient doesn't exist.");
    }
    return patient.get();
  }

  /**
   * Update the given patient.
   *
   * @param updatePatientDto the patient data to update
   * @return the updated patient
   */
  @Override
  public Patient updatePatient(UpdatePatientDto updatePatientDto) {

    // check if patient exist
    Optional<Patient> patientToUpdateOpt = patientRepository.findById(updatePatientDto.getId());
    if (patientToUpdateOpt.isEmpty()) {
      logger.warn(
          "Error, patient with familyName: "
              + updatePatientDto.getFamilyName()
              + " and givenName: "
              + updatePatientDto.getGivenName()
              + " doesn't exist.");
      throw new DataNotFoundException("KO, patient doesn't exist.");
    }
    // update fields
    Patient patient = patientToUpdateOpt.get();
    myCopyProperties(updatePatientDto, patient);
    // save
    logger.trace("Updating patient: " + patient);
    patientRepository.save(patient);
    return patient;
  }

  /**
   * Create a new patient.
   *
   * @param createPatientDto the patient to create
   * @return the patient saved in db
   */
  @Override
  public Patient createPatient(CreatePatientDto createPatientDto) {
    // check if patient already exist
    Optional<Patient> optionalPatient =
        patientRepository.findByFamilyNameAndGivenName(
            createPatientDto.getFamilyName(), createPatientDto.getGivenName());
    if (optionalPatient.isPresent()) {
      logger.warn(
          "Error, patient with familyName: "
              + createPatientDto.getFamilyName()
              + " and givenName: "
              + createPatientDto.getGivenName()
              + " already exist with id: "
              + optionalPatient.get().getId()
              + ".");
      throw new DataAlreadyExistException("KO, patient already exist.");
    }
    // map dto to entity
    Patient patient = new Patient();
    BeanUtils.copyProperties(createPatientDto, patient, "id");
    logger.trace("Save patient: " + patient.getFamilyName() + " - " + patient.getGivenName());
    // save
    patientRepository.save(patient);
    patient = this.getPatient(patient.getFamilyName(), patient.getGivenName());
    logger.trace(
        "Saved Patient: "
            + patient.getFamilyName()
            + " - "
            + patient.getGivenName()
            + " with id: "
            + patient.getId());
    return patient;
  }
}
