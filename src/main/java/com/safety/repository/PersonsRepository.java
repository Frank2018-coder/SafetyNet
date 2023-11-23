package com.safety.repository;

import com.safety.model.Adress;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safety.model.Person;

import java.util.Date;
import java.util.List;

@Repository @Hidden
public interface PersonsRepository extends CrudRepository<Person, Integer> {
    List<Person> findByFirstNameAndLastName(String firstName, String lastName);

    List<Person> findByFirstNameAndLastNameAndAdress(String lastName, String firstName, Adress adress);

    List<Person> findByFirstNameAndAdress(String firstName, Adress adress);


    @Modifying
    @Transactional
    @Query("delete from Person p where p.firstName=:firstName AND p.lastName=:lastName")
    void delelePersons(String firstName, String lastName);
}
