package com.safety.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safety.model.MedicalRecord;

@Repository @Hidden
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Integer> {

}
