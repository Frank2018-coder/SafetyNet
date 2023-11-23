package com.safety.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PersonWithMedicalDTO extends PersonDTO{
    private List<String> medications = new ArrayList<>();
    private List<String> allergies = new ArrayList<>();
}
