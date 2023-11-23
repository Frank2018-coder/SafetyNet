package com.safety.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data
@Table(name="adress")
public class Adress {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="adress_id")
   private int adressId;
   
   @Column(name="adress") @Size(max = 100)
   private String adress;
   
   @Column(name="city") @Size(max = 50)
   private String city = "defaultCity";
   
   @Column(name="zip") @Size(max = 20)
   private String zip = "defaultZip";

   @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER,
        mappedBy = "adress")
	private	List<Person> person = new ArrayList<>();
	
   @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER,
        mappedBy = "adress")
	private	List<FireStation> fireStation = new ArrayList<>();

}
