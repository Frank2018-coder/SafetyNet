package com.safety.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safety.model.Adress;

import java.util.List;

@Repository @Hidden
public interface AdressRepository extends CrudRepository<Adress, Integer>{

    List<Adress> findAllByAdress(String address);

    List<Adress> findBycity(String city);
}
