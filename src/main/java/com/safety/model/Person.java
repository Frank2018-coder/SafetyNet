package com.safety.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.safety.utility.Utility;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity @Data
@Table(name="persons")
public class Person {
  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@Column(name="person_id")
	private int personId;
	
	@Column(name="first_name") @Size(max = 50)
	private String firstName;
	
	@Column(name="last_name") @Size(max = 50)
	private String lastName;

	@Column(name="birth_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy", timezone = "UTC")
	private Date birthDate;
	
	@Column(name="phone") @Size(max = 20)
	private String phone;
	
	@Column(name="email") @Size(max = 100)
	private String email;

  	@OneToMany(
  		cascade = CascadeType.ALL,
	   	orphanRemoval = true,
	   	fetch = FetchType.EAGER,
		mappedBy = "person")
	private	List<MedicalRecord> medicalRecord = new ArrayList<>();
	
	  
	@ManyToOne(cascade = CascadeType.REFRESH )
    @JoinColumn( name="adress_id" )
    private Adress adress;

	public boolean isAdulte(){
		return (Utility.getNbreAnnee(this.birthDate,new Date())) > 18 ;
	}
}
