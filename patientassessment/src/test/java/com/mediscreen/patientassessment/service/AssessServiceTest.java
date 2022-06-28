package com.mediscreen.patientassessment.service;

import com.mediscreen.patientassessment.model.Assessement;
import com.mediscreen.patientassessment.repository.PatientHistoryRepository;
import com.mediscreen.patientassessment.service.impl.AssessServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class AssessServiceTest {

    private final String givenName="givenNameTest";
    private final String familyName="familyNameTest";
    private final int patientId = 2;
    private final String message="placeholder";

    @Mock private PatientHistoryRepository patientHistoryRepoMock;
    @InjectMocks
    AssessServiceImpl assessService;

    @Test
    void getAssessWithId(){

        // GIVEN
        var expected = new Assessement();
        expected.setAge(50);
        expected.setFamilyName(familyName);
        expected.setGivenName(givenName);
        expected.setMessage(message);
        // WHEN

        // THEN
        var actual = assessService.getAssessWithId(patientId);
        assertEquals(expected,actual);
    }


}