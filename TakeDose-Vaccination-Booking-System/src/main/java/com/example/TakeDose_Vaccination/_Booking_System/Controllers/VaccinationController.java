package com.example.TakeDose_Vaccination._Booking_System.Controllers;

import com.example.TakeDose_Vaccination._Booking_System.Exeptions.vaccinationAddressNotFound;
import com.example.TakeDose_Vaccination._Booking_System.Models.VaccinationCenter;
import com.example.TakeDose_Vaccination._Booking_System.Repository.VaccinationCenterRepository;
import com.example.TakeDose_Vaccination._Booking_System.Services.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vaccination")
public class VaccinationController {

    @Autowired
    private VaccinationService vaccinationService;

    @Autowired
    private VaccinationCenterRepository vaccinationCenterRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addCenter(
            @RequestBody VaccinationCenter vaccinationCenter) {

        try {

            String result =
                    vaccinationService.addCenter(vaccinationCenter);

            return ResponseEntity.ok(result);

        } catch (vaccinationAddressNotFound e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // NEW
    @GetMapping("/all")
    public ResponseEntity<List<VaccinationCenter>> getAllCenters() {

        return ResponseEntity.ok(
                vaccinationCenterRepository.findAll()
        );
    }
}