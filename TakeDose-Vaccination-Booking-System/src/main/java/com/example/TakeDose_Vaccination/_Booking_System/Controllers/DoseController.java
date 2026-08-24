package com.example.TakeDose_Vaccination._Booking_System.Controllers;

import com.example.TakeDose_Vaccination._Booking_System.Services.DoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dose")
public class DoseController {

    @Autowired
    private DoseService doseService;

    @PostMapping("/giveDose1")
    public String giveDose(
            @RequestParam("doseId") String doseId,
            @RequestParam("userId") Integer userId) {

        return doseService.giveDose(doseId, userId);
    }
}