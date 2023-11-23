package com.safety.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safety.model.Medication;

import java.util.List;

@Repository @Hidden
public interface MedicationsRepository extends CrudRepository<Medication, Integer> {

    List<Medication> findByDescription(String medicationName);
}
