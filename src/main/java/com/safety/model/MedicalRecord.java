package com.safety.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity @Data
@Table(name="medicalrecords")
public class MedicalRecord {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@Column(name="medical_record_id")
	private int medicalRecordId;

	@ManyToOne( cascade = CascadeType.REFRESH )
    @JoinColumn( name="person_id" )
    private Person person;

	@ManyToMany(
		cascade = CascadeType.REFRESH,
		fetch = FetchType.EAGER
	)
	@JoinTable(
		name = "allergie_medical_record",
		joinColumns = @JoinColumn(name="medical_record_id"),
		inverseJoinColumns = @JoinColumn(name = "allergie_id")
	)
	private List<Allergie> allergie = new ArrayList<>();
	
	@ManyToMany(
		cascade = CascadeType.REFRESH,
		fetch = FetchType.EAGER
	)
	@JoinTable(
		name = "medication_medical_record",
		joinColumns = @JoinColumn(name="medical_record_id"),
		inverseJoinColumns = @JoinColumn(name = "medication_id")
	)
	private List<Medication> medication = new ArrayList<>();
}
