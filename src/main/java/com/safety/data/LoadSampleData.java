package com.safety.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.data.dto.DataFile;
import com.safety.data.dto.FireStationDataJson;
import com.safety.data.dto.MedicalRecordDataJson;
import com.safety.data.dto.PersonDataJson;
import com.safety.model.*;
import com.safety.repository.*;
import com.safety.utility.Utility;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.util.List;

@Component
public class LoadSampleData {

    @Value("${safety.load.data}")
    private boolean loadData;
    private final ObjectMapper objectMapper;
    private final Resource jsonFile;
    private final PersonsRepository personsRepository;
    private final AdressRepository adressRepository;
    private final FireStationRepository fireStationRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final AllergiesRepository allergiesRepository;
    private final MedicationsRepository medicationsRepository;

    public LoadSampleData(ObjectMapper objectMapper,
                          @Value("classpath:data/sampleData.json") Resource jsonFile,
                          JdbcTemplate jdbcTemplate,
                          PersonsRepository personsRepository,
                          AdressRepository adressRepository,
                          FireStationRepository fireStationRepository,
                          MedicalRecordRepository medicalRecordRepository,
                          AllergiesRepository allergiesRepository,
                          MedicationsRepository medicationsRepository) {
        this.objectMapper = objectMapper;
        this.jsonFile = jsonFile;
        this.personsRepository = personsRepository;
        this.adressRepository = adressRepository;
        this.fireStationRepository = fireStationRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.medicationsRepository = medicationsRepository;
        this.allergiesRepository = allergiesRepository;
    }

    private void deleteAllData(){
//        medicalRecordRepository.deleteAll();
//        allergiesRepository.deleteAll();
//        medicationsRepository.deleteAll();
//        fireStationRepository.deleteAll();
//        personsRepository.deleteAll();
//        adressRepository.deleteAll();
    }
    public DataFile readJsonFile() throws IOException {
        return objectMapper.readValue(jsonFile.getInputStream(), DataFile.class);
    }

    @PostConstruct
    private void lodData(){
        try {
            if (loadData) {
                this.deleteAllData();
                DataFile dataFile = this.readJsonFile();
                this.loadPersons(dataFile.getPersons());
                this.loadFireStations(dataFile.getFirestations());
                this.loadMedicalRecords(dataFile.getMedicalrecords());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private void loadMedicalRecords(List<MedicalRecordDataJson> medicalrecords) {
        medicalrecords.forEach(medicalrecordJson -> {
            List<Person> personsList = personsRepository.findByFirstNameAndLastName(medicalrecordJson.getFirstName(), medicalrecordJson.getLastName());
            if(!personsList.isEmpty()){
                Person person = personsList.get(0);
                person.setBirthDate(medicalrecordJson.getBirthdate());
                person = personsRepository.save(person);

                MedicalRecord medicalRecord = new MedicalRecord();
                medicalRecord.setPerson(person);

                medicalrecordJson.getAllergies().forEach(allergieName -> {
                    List<Allergie> allergiesList = allergiesRepository.findByAllergieName(allergieName);
                    Allergie allergie = (allergiesList.isEmpty()) ? new Allergie() : allergiesList.get(0);
                    allergie.setAllergieName(allergieName);
                    allergie = allergiesRepository.save(allergie);
                    medicalRecord.getAllergie().add(allergie);
                });

                medicalrecordJson.getMedications().forEach(medicationName -> {
                    List<Medication> medicationsList = medicationsRepository.findByDescription(medicationName);
                    Medication medication = (medicationsList.isEmpty()) ? new Medication() : medicationsList.get(0);
                    medication.setDescription(medicationName);
                    medication = medicationsRepository.save(medication);
                    medicalRecord.getMedication().add(medication);
                });

                medicalRecordRepository.save(medicalRecord);
            }
        });
    }
    private void loadPersons(List<PersonDataJson> personDataJsons){
        personDataJsons.forEach(persJson -> {
            List<Adress> adressList = adressRepository.findAllByAdress(persJson.getAddress());
            Adress adress = adressList.isEmpty() ? new Adress() : adressList.get(0);
            adress.setAdress(persJson.getAddress());
            adress.setCity(persJson.getCity());
            adress.setZip(persJson.getZip());
            adress = adressRepository.save(adress);

            Person person = new Person();
            person.setEmail(persJson.getEmail());
            person.setFirstName(persJson.getFirstName());
            person.setLastName(persJson.getLastName());
            person.setPhone(persJson.getPhone());
            person.setBirthDate(Utility.subStractDay(-365 * 100));
            person.setAdress(adress);
            personsRepository.save(person);
        });
    }
    private void loadFireStations(List<FireStationDataJson> firestations) {
        firestations.forEach(fireStationJson -> {
            List<Adress> adressList = adressRepository.findAllByAdress(fireStationJson.getAddress());
            Adress adress = adressList.isEmpty() ? new Adress() : adressList.get(0);
            adress.setAdress(fireStationJson.getAddress());
            adress = adressRepository.save(adress);

            FireStation fireStation = new FireStation();
            fireStation.setStation(fireStationJson.getStation());
            fireStation.setAdress(adress);
            fireStationRepository.save(fireStation);
        });
    }
}
