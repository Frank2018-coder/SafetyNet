package com.safety.controller.crud;

import com.safety.dto.crud.MedicalRecordDTO;
import com.safety.service.MedicalRecordsService;
import com.safety.service.impl.MedicalRecordsServiceImpl;
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
@RequestMapping("/safetynet/api/v1.0/crud/medical-record")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordsService medicalRecordsService;

    @PostMapping("/")
    @Operation(summary = "Creation d'un dossier medical")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "dossier medical créé", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),})
    public String add(@Parameter(description = "Dossier medical a créer") @RequestBody MedicalRecordDTO medicalRecordDTO){
        log.info("-------PARAM: medicalRecordDTO = {}",medicalRecordDTO);
        return medicalRecordsService.add(medicalRecordDTO);
    }

    @PutMapping("/")
    @Operation(summary = "modification d'un dossier medical")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "dossier medical modifié", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),})
    public String update( @Parameter(description = "nouvelle information de le dossier medical") @RequestBody MedicalRecordDTO medicalRecordDTO){
        return medicalRecordsService.update(medicalRecordDTO);
    }

    @DeleteMapping("/")
    @Operation(summary = "suppression d'un dossier medical")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "personne supprimé", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),})
    public String delete(@Parameter(description = " dossier medical à supprimer") @RequestBody MedicalRecordDTO medicalRecordDTO){
        return medicalRecordsService.delete(medicalRecordDTO);
    }

}
