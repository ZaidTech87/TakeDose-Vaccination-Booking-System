package com.example.TakeDose_Vaccination._Booking_System.Controllers;

import com.example.TakeDose_Vaccination._Booking_System.DTOs.AssociateDoctorDto;
import com.example.TakeDose_Vaccination._Booking_System.Models.Doctor;
import com.example.TakeDose_Vaccination._Booking_System.Services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@CrossOrigin(origins = "http://localhost:5173")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/addDoctor")
    public ResponseEntity<String> addDoctor(
            @RequestBody Doctor doctor) {

        try {
            String response = doctorService.addDoctor(doctor);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/associateWithCenter")
    public ResponseEntity<String> associateDoctor(
            @RequestBody AssociateDoctorDto dto) {

        try {
            String result =
                    doctorService.associateDoctor(dto);

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // NEW
    @GetMapping("/all")
    public ResponseEntity<List<Doctor>> getAllDoctors() {

        return ResponseEntity.ok(
                doctorService.getAllDoctors()
        );
    }

    @GetMapping("/center/{centerId}")
    public ResponseEntity<List<Doctor>> getDoctorsByCenter(
            @PathVariable Integer centerId) {

        return ResponseEntity.ok(
                doctorService.getDoctorsByCenter(centerId)
        );
    }
}