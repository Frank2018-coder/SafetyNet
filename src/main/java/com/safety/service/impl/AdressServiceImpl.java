package com.safety.service.impl;

import com.safety.dto.ChildrenByadressDTO;
import com.safety.dto.PersonDTO;
import com.safety.model.Adress;
import com.safety.model.Person;
import com.safety.repository.AdressRepository;
import com.safety.service.AdressService;
import com.safety.utility.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service @Slf4j
public class AdressServiceImpl implements AdressService {

    @Autowired
    private AdressRepository adressRepository;
    @Override
    public ChildrenByadressDTO childrenAlert(String addressString) {
        ChildrenByadressDTO childrenByadressDTO = new ChildrenByadressDTO();
        log.info("\n\n");
        log.info("-------------------------DEBUT childrenAlert-------------------------------");
        log.info("-------PARAM: address = {}",addressString);
        try {
            List<Adress> adressList = adressRepository.findAllByAdress(addressString);
            for (Adress adress : adressList) {
                for (Person person : adress.getPerson()) {
                    PersonDTO personDTO = new PersonDTO();
                    personDTO.setAdress(adress.getAdress());
                    personDTO.setPhone(person.getPhone());
                    personDTO.setLastName(person.getLastName());
                    personDTO.setFirstName(person.getFirstName());
                    personDTO.setAge(Utility.getNbreAnnee(person.getBirthDate(), new Date()));
                    if (person.isAdulte())
                        childrenByadressDTO.getOtherFamilly().add(personDTO);
                    else
                        childrenByadressDTO.getChildren().add(personDTO);
                }
            }
        }catch (Exception ex){
            log.error("--ERROR childrenAlert: {}", ex);
        }
        log.info("-------ChildrenByadressDTO: {}",childrenByadressDTO);
        log.info("-------------------------FIN childrenAlert-------------------------------\n\n");
        return childrenByadressDTO;
    }
}
