package com.example.TakeDose_Vaccination._Booking_System.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vaccination_center")
@Getter
@Setter
public class VaccinationCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String centreName;

    private LocalTime openingTime;

    private LocalTime closingTime;

    private String address;

    private Integer doseCapacity;

    @OneToMany(mappedBy = "vaccinationCenter", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Doctor> doctorList = new ArrayList<>();
}