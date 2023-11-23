package com.safety.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class DataFile {
    private List<PersonDataJson> persons;
    private List<FireStationDataJson> firestations;
    private List<MedicalRecordDataJson> medicalrecords;
}
