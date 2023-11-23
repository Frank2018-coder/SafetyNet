package com.safety.repository;

import com.safety.model.Adress;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.safety.model.FireStation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository @Hidden
public interface FireStationRepository extends CrudRepository<FireStation, Integer> {

    List<FireStation> findByStation(String s);

    List<FireStation> findByStationAndAdress(String station, Adress adress);

    @Modifying @Transactional
    @Query("delete from FireStation f where f.station=:station AND f.adress.adressId=:adressId")
    void deleleStation(String station, int adressId);
}
