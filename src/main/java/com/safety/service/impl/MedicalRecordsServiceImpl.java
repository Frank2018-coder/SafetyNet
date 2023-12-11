package com.safety.service.impl;

import com.safety.dto.crud.MedicalRecordDTO;
import com.safety.model.Allergie;
import com.safety.model.MedicalRecord;
import com.safety.model.Medication;
import com.safety.model.Person;
import com.safety.repository.AllergiesRepository;
import com.safety.repository.MedicalRecordRepository;
import com.safety.repository.MedicationsRepository;
import com.safety.repository.PersonsRepository;
import com.safety.service.MedicalRecordsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class MedicalRecordsServiceImpl implements MedicalRecordsService {

    @Autowired
    private PersonsRepository personsRepository;
    @Autowired
    private AllergiesRepository allergiesRepository;
    @Autowired
    private MedicationsRepository medicationsRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Override
    public String add(MedicalRecordDTO medicalRecordDTO) {
        String message = "";
        log.info("\n\n");
        log.info("-------------------------DEBUT add-------------------------------");
        log.info("-------PARAM: medicalRecordDTO = {}",medicalRecordDTO);
        try{
            List<Person> personList = personsRepository.findByFirstNameAndLastName(medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
            if(!personList.isEmpty()){
                Person person = personList.get(0);
                List<Allergie> allergies = new ArrayList<>();
                List<Medication> medications = new ArrayList<>();

                for(String allergieString:medicalRecordDTO.getAllergies()){
                    List<Allergie> allergieList = allergiesRepository.findByAllergieName(allergieString);
                    Allergie allergie = new Allergie();
                    if(!allergieList.isEmpty()){
                        allergie = allergieList.get(0);
                    }
                    allergie.setAllergieName(allergieString);
                    allergies.add(allergiesRepository.save(allergie));
                }

                for(String medicationString:medicalRecordDTO.getMedications()){
                    List<Medication> medicationList = medicationsRepository.findByDescription(medicationString);
                    Medication medication = new Medication();
                    if(!medicationList.isEmpty()){
                        medication = medicationList.get(0);
                    }
                    medication.setDescription(medicationString);
                    medications.add(medicationsRepository.save(medication));
                }
                MedicalRecord medicalRecord = new MedicalRecord();
                medicalRecord.setMedication(medications);
                medicalRecord.setAllergie(allergies);
                medicalRecord.setPerson(person);
                medicalRecordRepository.save(medicalRecord);
                message = "L'enregistrement s'est déroulé avec succes....";
            }else{
                message = "Aucune personne trouvée avec: "+medicalRecordDTO.getFirstName()+" "+medicalRecordDTO.getLastName();
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
    public String update(MedicalRecordDTO medicalRecordDTO) {
        String message = "";
        log.info("\n\n");
        log.info("-------------------------DEBUT update-------------------------------");
        log.info("-------PARAM: medicalRecordDTO = {}",medicalRecordDTO);
        try{
            List<Person> personList = personsRepository.findByFirstNameAndLastName(medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
            if(!personList.isEmpty()){
                Person person = personList.get(0);
                List<Allergie> allergies = new ArrayList<>();
                List<Medication> medications = new ArrayList<>();

                List<MedicalRecord> medicalRecordList = person.getMedicalRecord();
                if(!medicalRecordList.isEmpty()){
                    MedicalRecord medicalRecord = medicalRecordList.get(0);

                    for(String allergieString:medicalRecordDTO.getAllergies()){
                        List<Allergie> allergieList = allergiesRepository.findByAllergieName(allergieString);
                        Allergie allergie = new Allergie();
                        if(!allergieList.isEmpty()){
                            allergie = allergieList.get(0);
                        }
                        allergie.setAllergieName(allergieString);
                        allergies.add(allergiesRepository.save(allergie));
                    }

                    for(String medicationString:medicalRecordDTO.getMedications()){
                        List<Medication> medicationList = medicationsRepository.findByDescription(medicationString);
                        Medication medication = new Medication();
                        if(!medicationList.isEmpty()){
                            medication = medicationList.get(0);
                        }
                        medication.setDescription(medicationString);
                        medications.add(medicationsRepository.save(medication));
                    }
                    medicalRecord.setMedication(medications);
                    medicalRecord.setAllergie(allergies);
                    medicalRecordRepository.save(medicalRecord);
                    message = "L'enregistrement s'est déroulé avec succes....";
                }else{
                    message = "Aucune dossier medical trouvé pour cette personne: "+medicalRecordDTO.getFirstName()+" "+medicalRecordDTO.getLastName();
                }
            }else{
                message = "Aucune personne trouvée avec: "+medicalRecordDTO.getFirstName()+" "+medicalRecordDTO.getLastName();
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
    public String delete(MedicalRecordDTO medicalRecordDTO) {
        String message = "";
        log.info("\n\n");
        log.info("-------------------------DEBUT delete-------------------------------");
        log.info("-------PARAM: medicalRecordDTO = {}", medicalRecordDTO);
        try {
            List<Person> personList = personsRepository.findByFirstNameAndLastName(medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
            if(!personList.isEmpty()){
                Person person = personList.get(0);
                medicationsRepository.deleteByPerson(person.getPersonId());
                message = "La suppression s'est déroulé avec succes....";
            }else{
                message = "Aucune personne trouvée avec: "+medicalRecordDTO.getFirstName()+" "+medicalRecordDTO.getLastName();
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
