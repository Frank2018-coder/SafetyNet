package com.safety.controller.crud;

import com.safety.dto.crud.FireStationDTO;
import com.safety.service.FireStationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController @Slf4j
@RequestMapping("/safetynet/api/v1.0/crud/fireStation")
public class FireStationCrudController {

    @Autowired
    private FireStationsService fireStationsService;

    @PostMapping("/")
    @Operation(summary = "Creation d'une station de service")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "station de service créé", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),})
    public String add(@Parameter(description = "Station a créer") @RequestBody FireStationDTO fireStationDTO){
        return fireStationsService.add(fireStationDTO);
    }

    @PutMapping("/{oldSation}")
    @Operation(summary = "modification d'une station de service")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "station de service modifiée", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),})
    public String update(@Parameter(description = "nouvelle information de la station") @PathVariable String oldSation,
         @Parameter(description = "nouvelle information de la station") @RequestBody FireStationDTO fireStationDTO){
        return fireStationsService.update(oldSation,fireStationDTO);
    }

    @DeleteMapping("/")
    @Operation(summary = "suppression d'une station de service")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "station de service supprimé", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),})
    public String delete(@Parameter(description = " station à supprimer") @RequestBody FireStationDTO fireStationDTO){
        return fireStationsService.delete(fireStationDTO);
    }

}
