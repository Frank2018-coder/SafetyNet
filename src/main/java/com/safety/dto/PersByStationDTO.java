package com.safety.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PersByStationDTO {
    private List<PersonDTO> listPersons = new ArrayList<>();
    private int nbreAdulte;
    private int nbreEnfant;
}
