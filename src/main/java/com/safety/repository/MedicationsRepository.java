package com.safety.repository;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safety.model.Medication;

import java.util.List;

@Repository @Hidden
public interface MedicationsRepository extends CrudRepository<Medication, Integer> {

    List<Medication> findByDescription(String medicationName);



    @Modifying
    @Transactional
    @Query("delete from MedicalRecord m where m.person.personId=:personId")
    void deleteByPerson(int personId);
}
