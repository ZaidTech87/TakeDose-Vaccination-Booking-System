package com.example.TakeDose_Vaccination._Booking_System.Services;

import com.example.TakeDose_Vaccination._Booking_System.Exeptions.DoctorNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;
    public String bookAppoinment(AppointmentReqDto appointmentReqDto)throws DoctorNotFound, UserNotFound{
        Optional<>

    }

}
