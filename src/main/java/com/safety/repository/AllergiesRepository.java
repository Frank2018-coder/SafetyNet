package com.safety.repository;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safety.model.Allergie;

import java.util.List;

@Repository @Hidden
public interface AllergiesRepository  extends CrudRepository<Allergie, Integer>{

    List<Allergie> findByAllergieName(String allergieName);
}
