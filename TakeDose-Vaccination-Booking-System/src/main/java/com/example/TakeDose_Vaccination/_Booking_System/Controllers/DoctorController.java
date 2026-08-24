package com.example.TakeDose_Vaccination._Booking_System.Controllers;

import com.example.TakeDose_Vaccination._Booking_System.DTOs.AssociateDoctorDto;
import com.example.TakeDose_Vaccination._Booking_System.Models.Doctor;
import com.example.TakeDose_Vaccination._Booking_System.Services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/addDoctor")
    public ResponseEntity<String> addDoctor(
            @RequestBody Doctor doctor) {

        try {

            String response =
                    doctorService.addDoctor(doctor);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/associateWithCenter")
    public ResponseEntity<String> associateDoctor(
            @RequestBody AssociateDoctorDto associateDoctorDto) {

        try {

            String result =
                    doctorService.associateDoctor(
                            associateDoctorDto
                    );

            return ResponseEntity.ok(result);

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}