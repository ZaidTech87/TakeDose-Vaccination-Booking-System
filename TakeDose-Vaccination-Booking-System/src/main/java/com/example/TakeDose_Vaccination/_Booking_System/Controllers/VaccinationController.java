package com.example.TakeDose_Vaccination._Booking_System.Controllers;

import com.example.TakeDose_Vaccination._Booking_System.Exeptions.vaccinationAddressNotFound;
import com.example.TakeDose_Vaccination._Booking_System.Models.VaccinationCenter;
import com.example.TakeDose_Vaccination._Booking_System.Services.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vaccination")
public class VaccinationController {
    @Autowired
    private VaccinationService vaccinationService;

    @PostMapping("/add")
    public ResponseEntity<String> addCenter(@RequestBody VaccinationCenter vaccinationCenter) {
        try {
            String res = vaccinationService.addCenter(vaccinationCenter);

            return  new ResponseEntity<>(res, HttpStatus.OK);

        } catch (vaccinationAddressNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
