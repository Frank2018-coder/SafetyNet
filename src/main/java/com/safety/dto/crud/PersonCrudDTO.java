package com.safety.dto.crud;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PersonCrudDTO {
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy", timezone = "UTC")
    private Date birthDate;
    private String phone;
    private String email;

    private AdressDTO adressDTO;
}
