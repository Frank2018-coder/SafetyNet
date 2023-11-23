package com.safety.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FoyerByStationDTO {
    private String adress;
    private List<PersonWithMedicalDTO> personWithMedicalDTOS = new ArrayList<>();
}
