package com.safety.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity @Data
@Table(name="firestations")
public class FireStation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@Column(name="fire_station_id")
	private int fireStationId;
	  
	@Column(name="station") @Size(max = 20)
	private String station;

	@ManyToOne( cascade = CascadeType.REFRESH )
    @JoinColumn( name="adress_id" )
    private Adress adress;
}
