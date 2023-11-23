package com.safety.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PersonWithEmailAndMedicalDTO extends PersonWithEmailDTO{
    private List<String> medications = new ArrayList<>();
    private List<String> allergies = new ArrayList<>();
}
