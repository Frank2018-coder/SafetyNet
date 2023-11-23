package com.safety.controller.crud;


import com.safety.dto.crud.PersonCrudDTO;
import com.safety.service.PersonsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/safetynet/api/v1.0/crud/persons")
public class PersonCrudController {

    @Autowired
    private PersonsService personsService;

    @PostMapping("/")
    @Operation(summary = "Creation d'une personne")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "personne créé", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),})
    public String add(@Parameter(description = "Personne a créer") @RequestBody PersonCrudDTO personCrudDTO){
        log.info("-------PARAM: address = {}",personCrudDTO);
        return personsService.add(personCrudDTO);
    }

    @PutMapping("/")
    @Operation(summary = "modification d'une personne")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "personne modifiée", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),})
    public String update( @Parameter(description = "nouvelle information de la personne") @RequestBody PersonCrudDTO personCrudDTO){
        return personsService.update(personCrudDTO);
    }

    @DeleteMapping("/")
    @Operation(summary = "suppression d'une personne")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "personne supprimé", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),})
    public String delete(@Parameter(description = " personne à supprimer") @org.springframework.web.bind.annotation.RequestBody PersonCrudDTO personCrudDTO){
        return personsService.delete(personCrudDTO);
    }



}
