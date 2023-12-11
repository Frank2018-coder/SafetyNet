package com.safety.dto.crud;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MedicalRecordDTO {
    private String firstName;
    private String lastName;
    private List<String> medications = new ArrayList<>();
    private List<String> allergies = new ArrayList<>();
}


