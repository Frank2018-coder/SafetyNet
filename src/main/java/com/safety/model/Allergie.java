package com.safety.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity @Data
@Table(name="allergies")
public class Allergie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@Column(name="allergie_id")
	private int allergieId;
	  
	@Column(name="allergie_name") @Size(max = 50)
    private String allergieName;

	@ManyToMany(mappedBy = "allergie")
	private List<MedicalRecord> medicalRecord = new ArrayList<>();
}
