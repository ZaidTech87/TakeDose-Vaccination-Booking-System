package com.example.TakeDose_Vaccination._Booking_System.Controllers;

import com.example.TakeDose_Vaccination._Booking_System.Models.Doctor;
import com.example.TakeDose_Vaccination._Booking_System.Services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    DoctorService doctorService;
    @GetMapping("/addDoctor")
    public String addDoctor(@RequestBody Doctor doctor){
        try{
            String responce = doctorService.addDoctor(doctor);
            return responce;
        }catch (Exception e){
            return e.getMessage();
        }

    }
}
