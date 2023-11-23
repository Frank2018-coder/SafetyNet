package com.safety.service;

import com.safety.dto.PersonWithEmailAndMedicalDTO;
import com.safety.dto.crud.PersonCrudDTO;

import java.util.List;

public interface PersonsService {
    List<PersonWithEmailAndMedicalDTO> personInfo(String firstName, String lastName);

    List<String> communityEmail(String city);

    String add(PersonCrudDTO personCrudDTO);

    String update(PersonCrudDTO personCrudDTO);

    String delete(PersonCrudDTO personCrudDTO);

    // String delete(PersonCrudDTO personCrudDTO);
}
