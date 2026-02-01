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
    DoctorService doctorService;
    @PostMapping("/addDoctor")
    public String addDoctor(@RequestBody Doctor doctor){
        try{
            String responce = doctorService.addDoctor(doctor);
            return responce;
        }catch (Exception e){
            return e.getMessage();
        }

    }

    @PostMapping("/associateWithCenter")
    public ResponseEntity<String> associateDoctor(@RequestBody AssociateDoctorDto associateDoctorDto){

        try {
            String result = doctorService.associateDoctor(associateDoctorDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
