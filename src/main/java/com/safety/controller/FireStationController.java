package com.safety.controller;

import com.safety.dto.FoyerByStationDTO;
import com.safety.dto.PersAndFireStationByAdressDTO;
import com.safety.dto.PersByStationDTO;
import com.safety.service.FireStationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController @Slf4j
@RequestMapping("/safetynet/api/v1.0/fireStation")
public class FireStationController {

    @Autowired
    private FireStationsService fireStationsService;

    @GetMapping("/persons")
    @Operation(summary = "Lister des personnes connaissant le numero de station: elle fournit aussi le nombre d'enfant et le nombre d'adulte")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "find Pers By Station", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PersByStationDTO.class)) }),})
    public PersByStationDTO findPersByStation(@Parameter(description = "le numero de la station") @RequestParam(value = "stationNumber", defaultValue = "1") int stationNumber){
        return fireStationsService.getPersonByStation(stationNumber);
    }

    @GetMapping("/phoneAlert")
    @Operation(summary = "Lister des telephone connaissant le numero de station")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "find Pers By Station", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }),})
    public List<String> phoneAlert(@Parameter(description = "le numero de la station") @RequestParam(value = "stationNumber", defaultValue = "1") int stationNumber){
        return fireStationsService.getPhoneAlert(stationNumber);
    }

    @GetMapping("/fire")
    @Operation(summary = "Lister des personnes avec antécédents medicaux et liste des stations de service")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "find Pers By Adress", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PersAndFireStationByAdressDTO.class)) }),})
    public PersAndFireStationByAdressDTO persAndFireStationByAdress(@Parameter(description = "address of location") @RequestParam(value = "address", defaultValue = "1509 Culver St") String address){
        return fireStationsService.persAndFireStationByAdress(address);
    }

    @GetMapping("/flood/stations")
    @Operation(summary = "Lister des foyers regroupé par Adress et contenat la liste des personnes avec antécédents medicaux")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "find Pers By Adress", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }),})
    public List<FoyerByStationDTO> FoyerByStation(@Parameter(description = "liste des stations de service") @RequestParam(value = "stations") List<String> stations){
        return fireStationsService.FoyerByStation(stations);
    }
}
