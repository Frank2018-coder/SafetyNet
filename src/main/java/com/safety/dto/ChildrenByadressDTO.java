package com.safety.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChildrenByadressDTO {
    List<PersonDTO> children = new ArrayList<>();
    List<PersonDTO> otherFamilly = new ArrayList<>();
}
