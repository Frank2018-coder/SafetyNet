package com.safety.service.impl;

import com.safety.dto.*;
import com.safety.dto.crud.PersonCrudDTO;
import com.safety.model.*;
import com.safety.repository.AdressRepository;
import com.safety.repository.PersonsRepository;
import com.safety.service.PersonsService;
import com.safety.utility.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service @Slf4j
public class PersonsServiceImpl implements PersonsService {

    @Autowired
    private PersonsRepository personsRepository;

    @Autowired
    private AdressRepository adressRepository;
    @Override
    public List<PersonWithEmailAndMedicalDTO> personInfo(String firstName, String lastName) {
        List<PersonWithEmailAndMedicalDTO> personWithEmailAndMedicalDTOS = new ArrayList<>();
        log.info("\n\n");
        log.info("-------------------------DEBUT personInfo-------------------------------");
        log.info("-------PARAM: firstName = {}, lastName = {}",firstName,lastName);
        try {
            List<Person> personList = personsRepository.findByFirstNameAndLastName(firstName, lastName);
            for (Person person : personList) {
                PersonWithEmailAndMedicalDTO personWithEmailAndMedicalDTO = new PersonWithEmailAndMedicalDTO();
                personWithEmailAndMedicalDTO.setAdress(person.getAdress().getAdress());
                personWithEmailAndMedicalDTO.setAge(Utility.getNbreAnnee(person.getBirthDate(), new Date()));
                personWithEmailAndMedicalDTO.setPhone(person.getPhone());
                personWithEmailAndMedicalDTO.setFirstName(person.getFirstName());
                personWithEmailAndMedicalDTO.setLastName(person.getLastName());
                personWithEmailAndMedicalDTO.setEmail(person.getEmail());
                for (MedicalRecord medicalRecord : person.getMedicalRecord()) {
                    for (Allergie allergie : medicalRecord.getAllergie()) {
                        personWithEmailAndMedicalDTO.getAllergies().add(allergie.getAllergieName());
                    }
                    for (Medication medication : medicalRecord.getMedication()) {
                        personWithEmailAndMedicalDTO.getMedications().add(medication.getDescription());
                    }
                }
                personWithEmailAndMedicalDTOS.add(personWithEmailAndMedicalDTO);
            }
        }catch (Exception ex){
            log.error("--ERROR personInfo: {}", ex);
        }
        log.info("-------personWithEmailAndMedicalDTOS: {}",personWithEmailAndMedicalDTOS);
        log.info("-------------------------FIN personInfo-------------------------------\n\n");
        return personWithEmailAndMedicalDTOS;
    }

    @Override
    public List<String> communityEmail(String city) {
        List<String> mails = new ArrayList<>();
        log.info("\n\n");
        log.info("-------------------------DEBUT communityEmail-------------------------------");
        log.info("-------PARAM: city = {}",city);
        try {
            List<Adress> adressList = adressRepository.findBycity(city);
            for (Adress adress:adressList){
                for (Person person:adress.getPerson()){
                    if(!mails.contains(person.getEmail())){
                        mails.add(person.getEmail());
                    }
                }
            }
        }catch (Exception ex){
            log.error("--ERROR communityEmail: {}", ex);
        }
        log.info("-------mails: {}",mails);
        log.info("-------------------------FIN communityEmail-------------------------------\n\n");
        return mails;
    }

    @Override
    public String add(PersonCrudDTO personCrudDTO) {
        String message = "";
        log.info("\n\n");
        log.info("-------------------------DEBUT add-------------------------------");
        log.info("-------PARAM: address = {}",personCrudDTO);
        try {
            List<Adress> adressList = adressRepository.findAllByAdress(personCrudDTO.getAdressDTO().getAdress());
            Adress adress;
            if (adressList.isEmpty()) {
                adress = new Adress();
                adress.setAdress(personCrudDTO.getAdressDTO().getAdress());
                adress.setCity(personCrudDTO.getAdressDTO().getCity());
                adress.setZip(personCrudDTO.getAdressDTO().getZip());
                adress = adressRepository.save(adress);
            } else {
                adress = adressList.get(0);
            }

            List<Person> personList = personsRepository.findByFirstNameAndLastName(personCrudDTO.getLastName(), personCrudDTO.getFirstName());
            if(personList.isEmpty()) {
                Person person = new Person();
                person.setFirstName(personCrudDTO.getFirstName());
                person.setLastName(personCrudDTO.getLastName());
                person.setBirthDate(personCrudDTO.getBirthDate());
                person.setEmail(personCrudDTO.getEmail());
                person.setPhone(personCrudDTO.getPhone());
                person.setAdress(adress);
                personsRepository.save(person);
                message = "L'enregistrement s'est déroulé avec succes....";
            }else {
                message = "Une personne avec le nom: "+personCrudDTO.getFirstName() + ", de prénom: " + personCrudDTO.getLastName() +" et vivant à l'adresse "+personCrudDTO.getAdressDTO().getAdress()+" existe déja....";
            }
        }catch (Exception ex){
            log.error("--ERROR add: {}", ex);
            message = "une erreur est survenu, merci de réessayer plutard ou contacter un administrateur";
        }
        log.info("-------message: {}",message);
        log.info("-------------------------FIN add-------------------------------\n\n");
        return message;
    }

    @Override
    public String update(PersonCrudDTO personCrudDTO) {
        String message = "";
        log.info("\n\n");
        log.info("-------------------------DEBUT update-------------------------------");
        log.info("-------PARAM:  personCrudDTO = {}", personCrudDTO);
        try {

            List<Person> personList = personsRepository.findByFirstNameAndLastName(personCrudDTO.getLastName(), personCrudDTO.getFirstName());
            if (!personList.isEmpty()) {
                List<Adress> adressList = adressRepository.findAllByAdress(personCrudDTO.getAdressDTO().getAdress());
                Adress adress;
                if (adressList.isEmpty()) {
                    adress = new Adress();
                    adress.setAdress(personCrudDTO.getAdressDTO().getAdress());
                    adress.setCity(personCrudDTO.getAdressDTO().getCity());
                    adress.setZip(personCrudDTO.getAdressDTO().getZip());
                    adress = adressRepository.save(adress);
                } else {
                    adress = adressList.get(0);
                }
                    Person person = personList.get(0);
                    person.setEmail(personCrudDTO.getEmail());
                    person.setPhone(personCrudDTO.getPhone());
                    person.setBirthDate(personCrudDTO.getBirthDate());
                    person.setAdress(adress);
                    personsRepository.save(person);
                    message = "L'enregistrement s'est déroulé avec succes....";

            }else{
                message = "La personne nommée : "+personCrudDTO.getFirstName()+" et situé à l'adresse "+personCrudDTO.getAdressDTO().getAdress()+" n'existe pas....";

            }
        }catch (Exception ex){
            log.error("--ERROR update: {}", ex);
            message = "une erreur est survenu, merci de réessayer plutard ou contacter un administrateur";
        }
        log.info("-------message: {}",message);
        log.info("-------------------------FIN update-------------------------------\n\n");
        return message;
    }

    @Override
    public String delete(PersonCrudDTO personCrudDTO) {
        String message = "";
        log.info("\n\n");
        log.info("-------------------------DEBUT delete-------------------------------");
        log.info("-------PARAM: personCrudDTO = {}", personCrudDTO);
        try {

                List<Person> personList = personsRepository.findByFirstNameAndLastName(personCrudDTO.getFirstName(), personCrudDTO.getLastName());
                if(!personList.isEmpty()) {
                    Person person = personList.get(0);
                    log.info("TO DELETE: {} : {} : {} : {}", person.getPersonId(), person.getFirstName(), person.getAdress().getAdress(), person.getAdress().getAdressId());
                    personsRepository.delelePersons(personCrudDTO.getFirstName(), personCrudDTO.getLastName());
                    log.info("END TO DELETE");
                    message = "La suppression s'est déroulé avec succes....";
                }else {
                    message = "La personne: "+personCrudDTO.getFirstName()+" et situé à l'adresse "+personCrudDTO.getAdressDTO().getAdress()+" n'existe pas....";
                }
        }catch (Exception ex){
            log.error("--ERROR delete: {}", ex);
            message = "une erreur est survenu, merci de réessayer plutard ou contacter un administrateur";
        }
        log.info("-------message: {}",message);
        log.info("-------------------------FIN delete-------------------------------\n\n");
        return message;
    }


}
