package com.safety.controller;

import com.safety.dto.ChildrenByadressDTO;
import com.safety.dto.PersonWithEmailAndMedicalDTO;
import com.safety.service.PersonsService;
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

@RestController
@Slf4j
@RequestMapping("/safetynet/api/v1.0/person")
public class PersonController {

    @Autowired
    private PersonsService personsService;

    @GetMapping("/personInfo")
    @Operation(summary = "Connaissant le nom et prenom, retouner toutes les personnes avec leur dossier medicaux y compril leur mail")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "find person info", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }),})
    public List<PersonWithEmailAndMedicalDTO> personInfo(
            @Parameter(description = "nom de l'individu") @RequestParam(value = "firstName") String firstName,
            @Parameter(description = "prenom de l'individu") @RequestParam(value = "lastName") String lastName){
        return personsService.personInfo(firstName, lastName);
    }

    @GetMapping("/communityEmail")
    @Operation(summary = "Connaissant le nom d'une ville, retouner toutes les mail des personnes s'y trouvant")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "find email", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }),})
    public List<String> communityEmail(@Parameter(description = "ville de residence des habitants") @RequestParam(value = "city") String city){
        return personsService.communityEmail(city);
    }

}
