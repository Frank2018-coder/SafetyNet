package com.safety.service;

import com.safety.dto.FoyerByStationDTO;
import com.safety.dto.PersAndFireStationByAdressDTO;
import com.safety.dto.PersByStationDTO;
import com.safety.dto.crud.FireStationDTO;

import java.util.List;

public interface FireStationsService {
    PersByStationDTO getPersonByStation(int stationNumber);

    List<String> getPhoneAlert(int stationNumber);

    PersAndFireStationByAdressDTO persAndFireStationByAdress(String address);

    List<FoyerByStationDTO> FoyerByStation(List<String> stations);

    String add(FireStationDTO fireStationDTO);

    String update(String oldSation, FireStationDTO fireStationDTO);

    String delete(FireStationDTO fireStationDTO);
}
