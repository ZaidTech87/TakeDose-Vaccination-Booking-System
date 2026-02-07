package com.example.TakeDose_Vaccination._Booking_System.Controllers;

import com.example.TakeDose_Vaccination._Booking_System.Models.VaccinationCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vaccination")
public class VaccinationController {
    @Autowired
    private VaccinationController vaccinationController;

    public ResponseEntity<String> addCenter(@RequestBody VaccinationCenter vaccinationCenter) {
        try{

            String res = vaccinationController.addCenter(vaccinationCenter);
            return ResponseEntity<>(res,HttpStatus.OK);

    }
}
