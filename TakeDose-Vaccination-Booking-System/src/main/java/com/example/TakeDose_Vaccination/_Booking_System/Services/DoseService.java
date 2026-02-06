package com.example.TakeDose_Vaccination._Booking_System.Services;

import com.example.TakeDose_Vaccination._Booking_System.Models.Dose;
import com.example.TakeDose_Vaccination._Booking_System.Repository.DoseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DoseService {
    @Autowired
    DoseRepository doseRepository;
    public String giveDose(){}

}
