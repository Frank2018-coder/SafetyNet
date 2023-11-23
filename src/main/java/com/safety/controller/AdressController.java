package com.safety.controller;

import com.safety.dto.ChildrenByadressDTO;
import com.safety.service.AdressService;
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

@RestController
@Slf4j
@RequestMapping("/safetynet/api/v1.0/address")
public class AdressController {

    @Autowired
    private AdressService adressService;

    @GetMapping("/childrenAlert")
    @Operation(summary = "Lister des enfants connaissant l'adresse. Elle fournit aussi les autres membre de la famille")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "find Child By Adress", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ChildrenByadressDTO.class)) }),})
    public ChildrenByadressDTO findChildrenByAdress(@Parameter(description = "address of location") @RequestParam(value = "address", defaultValue = "1509 Culver St") String address){
        return adressService.childrenAlert(address);
    }
}
