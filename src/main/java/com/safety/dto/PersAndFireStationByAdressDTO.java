package com.safety.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PersAndFireStationByAdressDTO {
    List<PersonWithMedicalDTO> persons = new ArrayList<>();
    List<String> fireStationNumbers = new ArrayList<>();
}
