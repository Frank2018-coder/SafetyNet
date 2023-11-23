package com.safety.service.impl;

import com.safety.dto.*;
import com.safety.dto.crud.FireStationDTO;
import com.safety.model.*;
import com.safety.repository.AdressRepository;
import com.safety.repository.FireStationRepository;
import com.safety.service.FireStationsService;
import com.safety.utility.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

@Service @Slf4j
public class FireStationsServiceImpl implements FireStationsService {

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private AdressRepository adressRepository;

    @Override
    public PersByStationDTO getPersonByStation(int stationNumber) {
        PersByStationDTO persByStationDTO = new PersByStationDTO();
        log.info("\n\n");
        log.info("-------------------------DEBUT getPersonByStation-------------------------------");
        log.info("-------PARAM: stationNumber = {}",stationNumber);
        try {
            List<FireStation> fireStationsList = fireStationRepository.findByStation(""+stationNumber);
            int nbreEnfant = 0;
            int nbreAdulte = 0;
            List<PersonDTO> personDTOList = new ArrayList<>();
            for(FireStation fireStation:fireStationsList){
                List<Person> personsList = fireStation.getAdress().getPerson();
                for (Person pers : personsList) {
                    if (pers.isAdulte())
                        nbreAdulte++;
                    else nbreEnfant++;
                    PersonDTO personDTO = new PersonDTO();
                    personDTO.setAge(Utility.getNbreAnnee(pers.getBirthDate(), new Date()));
                    personDTO.setAdress(pers.getAdress().getAdress());
                    personDTO.setPhone(pers.getPhone());
                    personDTO.setFirstName(pers.getFirstName());
                    personDTO.setLastName(pers.getLastName());
                    personDTOList.add(personDTO);
                }
                log.info("-------StaionAdress: {}, NbrePersTotal: {}, nbreEnfant-cumul: {} et nbreAdulte-cumul: {}", fireStation.getAdress().getAdress(), personsList.size(), nbreEnfant, nbreAdulte);
            }
            persByStationDTO.setListPersons(personDTOList);
            persByStationDTO.setNbreAdulte(nbreAdulte);
            persByStationDTO.setNbreEnfant(nbreEnfant);
        }catch (Exception ex){
            log.error("--ERROR getPersonByStation: {}", ex);
        }
        log.info("-------persByStationDTO: {}",persByStationDTO);
        log.info("-------------------------FIN getPersonByStation-------------------------------\n\n");
        return persByStationDTO;
    }

    @Override
    public List<String> getPhoneAlert(int stationNumber) {
        List<String> phoneList = new ArrayList<>();
        log.info("\n\n");
        log.info("-------------------------DEBUT getPhoneAlert-------------------------------");
        log.info("-------PARAM: stationNumber = {}",stationNumber);
        try {
            List<FireStation> fireStationsList = fireStationRepository.findByStation(""+stationNumber);
            for(FireStation fireStation:fireStationsList){
                for (Person pers : fireStation.getAdress().getPerson()) {
                    phoneList.add(pers.getPhone());
                }
            }
        }catch (Exception ex){
            log.error("--ERROR getPersonByStation: {}", ex);
        }
        log.info("-------phoneList: {}",phoneList);
        log.info("-------------------------FIN getPhoneAlert-------------------------------\n\n");
        return phoneList;
    }

    @Override
    public PersAndFireStationByAdressDTO persAndFireStationByAdress(String address) {
        PersAndFireStationByAdressDTO persAndFireStationByAdressDTO = new PersAndFireStationByAdressDTO();
        log.info("\n\n");
        log.info("-------------------------DEBUT persAndFireStationByAdress-------------------------------");
        log.info("-------PARAM: address = {}",address);
        try {
            List<Adress> adressList = adressRepository.findAllByAdress(address);
            for (Adress adress : adressList) {
                for (FireStation fireStation : adress.getFireStation()) {
                    persAndFireStationByAdressDTO.getFireStationNumbers().add(fireStation.getStation());
                }
                for (Person person : adress.getPerson()) {
                    PersonWithMedicalDTO personWithMedicalDTO = new PersonWithMedicalDTO();
                    personWithMedicalDTO.setAdress(adress.getAdress());
                    personWithMedicalDTO.setAge(Utility.getNbreAnnee(person.getBirthDate(), new Date()));
                    personWithMedicalDTO.setPhone(person.getPhone());
                    personWithMedicalDTO.setFirstName(person.getFirstName());
                    personWithMedicalDTO.setLastName(person.getLastName());
                    for (MedicalRecord medicalRecord : person.getMedicalRecord()) {
                        for (Allergie allergie : medicalRecord.getAllergie()) {
                            personWithMedicalDTO.getAllergies().add(allergie.getAllergieName());
                        }
                        for (Medication medication : medicalRecord.getMedication()) {
                            personWithMedicalDTO.getMedications().add(medication.getDescription());
                        }
                    }
                    persAndFireStationByAdressDTO.getPersons().add(personWithMedicalDTO);
                }
            }
        }catch (Exception ex){
            log.error("--ERROR persAndFireStationByAdress: {}", ex);
        }
        log.info("-------persAndFireStationByAdressDTO: {}",persAndFireStationByAdressDTO);
        log.info("-------------------------FIN persAndFireStationByAdress-------------------------------\n\n");
        return persAndFireStationByAdressDTO;
    }

    @Override
    public List<FoyerByStationDTO> FoyerByStation(List<String> stations) {
        List<FoyerByStationDTO> foyerByStationDTOList = new ArrayList<>();
        log.info("\n\n");
        log.info("-------------------------DEBUT FoyerByStation-------------------------------");
        log.info("-------PARAM: address = {}",stations);
        try {
            TreeMap<String, List<PersonWithMedicalDTO>> dataMap = new TreeMap<>();
            for (String station:stations) {
                List<FireStation> fireStationList = fireStationRepository.findByStation(station);
                for (FireStation fireStation:fireStationList){
                    String adress = fireStation.getAdress().getAdress();
                    PersAndFireStationByAdressDTO persAndFireStationByAdressDTO = this.persAndFireStationByAdress(adress);
                    dataMap.put(adress, persAndFireStationByAdressDTO.getPersons());
                }
            }
            for (String adress : dataMap.keySet()){
                FoyerByStationDTO foyerByStationDTO = new FoyerByStationDTO();
                foyerByStationDTO.setAdress(adress);
                foyerByStationDTO.setPersonWithMedicalDTOS(dataMap.get(adress));
                foyerByStationDTOList.add(foyerByStationDTO);
            }
        }catch (Exception ex){
            log.error("--ERROR FoyerByStation: {}", ex);
        }
        log.info("-------foyerByStationDTOList: {}",foyerByStationDTOList);
        log.info("-------------------------FIN FoyerByStation-------------------------------\n\n");
        return foyerByStationDTOList;
    }

    @Override
    public String add(FireStationDTO fireStationDTO) {
        String message = "";
        log.info("\n\n");
        log.info("-------------------------DEBUT add-------------------------------");
        log.info("-------PARAM: address = {}",fireStationDTO);
        try {
                List<Adress> adressList = adressRepository.findAllByAdress(fireStationDTO.getAdress().getAdress());
                Adress adress;
                if (adressList.isEmpty()) {
                    adress = new Adress();
                    adress.setAdress(fireStationDTO.getAdress().getAdress());
                    adress.setCity(fireStationDTO.getAdress().getCity());
                    adress.setZip(fireStationDTO.getAdress().getZip());
                    adress = adressRepository.save(adress);
                } else {
                    adress = adressList.get(0);
                }
                List<FireStation> fireStationList = fireStationRepository.findByStationAndAdress(fireStationDTO.getStation(),adress);
                if(fireStationList.isEmpty()) {
                    FireStation fireStation = new FireStation();
                    fireStation.setStation(fireStationDTO.getStation());
                    fireStation.setAdress(adress);
                    fireStationRepository.save(fireStation);
                    message = "L'enregistrement s'est déroulé avec succes....";
                }else {
                    message = "Une station avec le numero: "+fireStationDTO.getStation()+" et situé à l'adresse "+fireStationDTO.getAdress().getAdress()+" existe déja....";
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
    public String update(String oldSation, FireStationDTO fireStationDTO) {
        String message = "";
        log.info("\n\n");
        log.info("-------------------------DEBUT update-------------------------------");
        log.info("-------PARAM: oldSation = {}, fireStationDTO = {}",oldSation, fireStationDTO);
        try {
            List<Adress> adressList = adressRepository.findAllByAdress(fireStationDTO.getAdress().getAdress());
            if (!adressList.isEmpty()) {
                Adress adress = adressList.get(0);
                List<FireStation> fireStationList = fireStationRepository.findByStationAndAdress(oldSation,adress);
                if(!fireStationList.isEmpty()) {
                    FireStation fireStation = fireStationList.get(0);
                    fireStation.setStation(fireStationDTO.getStation());
                    fireStationRepository.save(fireStation);
                    message = "L'enregistrement s'est déroulé avec succes....";
                }else {
                    message = "La station avec le numero: "+oldSation+" et situé à l'adresse "+fireStationDTO.getAdress().getAdress()+" n'existe pas....";
                }
            }else{
                message = "Adress inexistante: "+fireStationDTO.getAdress().getAdress();
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
    public String delete(FireStationDTO fireStationDTO) {
        String message = "";
        log.info("\n\n");
        log.info("-------------------------DEBUT delete-------------------------------");
        log.info("-------PARAM: fireStationDTO = {}", fireStationDTO);
        try {
            List<Adress> adressList = adressRepository.findAllByAdress(fireStationDTO.getAdress().getAdress());
            if (!adressList.isEmpty()) {
                Adress adress = adressList.get(0);
                List<FireStation> fireStationList = fireStationRepository.findByStationAndAdress(fireStationDTO.getStation(),adress);
                if(!fireStationList.isEmpty()) {
                    FireStation fireStation = fireStationList.get(0);
                    log.info("TO DELETE: {} : {} : {} : {}", fireStation.getFireStationId(), fireStation.getStation(), fireStation.getAdress().getAdress(), fireStation.getAdress().getAdressId());
                    fireStationRepository.deleleStation(fireStation.getStation(), fireStation.getAdress().getAdressId());
                    log.info("END TO DELETE");
                    message = "La suppression s'est déroulé avec succes....";
                }else {
                    message = "La station avec le numero: "+fireStationDTO.getStation()+" et situé à l'adresse "+fireStationDTO.getAdress().getAdress()+" n'existe pas....";
                }
            }else{
                message = "Adress inexistante: "+fireStationDTO.getAdress().getAdress();
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
