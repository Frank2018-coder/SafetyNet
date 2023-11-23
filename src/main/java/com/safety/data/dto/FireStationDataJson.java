package com.safety.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FireStationDataJson {
    @JsonProperty("address")
    private String address;
    @JsonProperty("station")
    private String station;
}
