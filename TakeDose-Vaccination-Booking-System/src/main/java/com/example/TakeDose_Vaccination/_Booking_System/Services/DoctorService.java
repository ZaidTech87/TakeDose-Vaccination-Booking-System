package com.example.TakeDose_Vaccination._Booking_System.Services;

import com.example.TakeDose_Vaccination._Booking_System.Exeptions.DoctorAlreadyExistExeption;
import com.example.TakeDose_Vaccination._Booking_System.Exeptions.EmailIdEmptyExeption;
import com.example.TakeDose_Vaccination._Booking_System.Models.Doctor;
import com.example.TakeDose_Vaccination._Booking_System.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public String addDoctor(Doctor doctor) throws EmailIdEmptyExeption, DoctorAlreadyExistExeption {
        if(doctor.getEmailId() == null){
            throw new EmailIdEmptyExeption("Email id is Mandatory");
        }
        if(doctorRepository.findByEmailId(doctor.getEmailId()) !=null) {
            throw new  DoctorAlreadyExistExeption("Doctor with this Email Id Already exists");
        }
        doctorRepository.save(doctor);
        return "Doctor has baeen added";

    }
}
