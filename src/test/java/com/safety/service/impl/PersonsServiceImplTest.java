package com.safety.service.impl;

import com.safety.dto.PersonDTO;
import com.safety.dto.crud.AdressDTO;
import com.safety.dto.crud.PersonCrudDTO;
import com.safety.model.Adress;
import com.safety.model.Person;
import com.safety.repository.PersonsRepository;
import com.safety.service.PersonsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
class PersonsServiceImplTest {

    @Autowired
    private PersonsService personsService;


    @Test
    public void personInfo() {

    }

    @Test
    public void communityEmail() {

    }

    @Test
    public void add() {
        AdressDTO adressDTO = new AdressDTO();
        adressDTO.setAdress("PETE");
        adressDTO.setCity("Bandjoun");
        adressDTO.setZip("123");

        PersonCrudDTO personCrudDTO = new PersonCrudDTO();
        personCrudDTO.setAdressDTO(adressDTO);
        personCrudDTO.setFirstName("MBOUMDA");
        personCrudDTO.setLastName("FRANK");
        personCrudDTO.setBirthDate(new Date());
        personCrudDTO.setPhone("691964901");
        personCrudDTO.setEmail("frank@gmail.com");



    }

    @Test
    public void update() {

    }

    @Test
    public void delete() {

    }

}