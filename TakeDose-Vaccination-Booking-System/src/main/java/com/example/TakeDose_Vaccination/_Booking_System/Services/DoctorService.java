package com.example.TakeDose_Vaccination._Booking_System.Services;

import com.example.TakeDose_Vaccination._Booking_System.DTOs.AssociateDoctorDto;
import com.example.TakeDose_Vaccination._Booking_System.Exeptions.CenterNotFound;
import com.example.TakeDose_Vaccination._Booking_System.Exeptions.DoctorAlreadyExistExeption;
import com.example.TakeDose_Vaccination._Booking_System.Exeptions.DoctorNotFound;
import com.example.TakeDose_Vaccination._Booking_System.Exeptions.EmailIdEmptyExeption;
import com.example.TakeDose_Vaccination._Booking_System.Models.Doctor;
import com.example.TakeDose_Vaccination._Booking_System.Models.VaccinationCenter;
import com.example.TakeDose_Vaccination._Booking_System.Repository.DoctorRepository;
import com.example.TakeDose_Vaccination._Booking_System.Repository.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private VaccinationCenterRepository vaccinationCenterRepository;

    public String addDoctor(Doctor doctor)
            throws EmailIdEmptyExeption, DoctorAlreadyExistExeption {

        if (doctor.getEmailId() == null ||
                doctor.getEmailId().isBlank()) {

            throw new EmailIdEmptyExeption("Email id is mandatory");
        }

        if (doctorRepository.existsByEmailId(doctor.getEmailId())) {
            throw new DoctorAlreadyExistExeption(
                    "Doctor with this Email Id already exists"
            );
        }

        doctorRepository.save(doctor);

        return "Doctor has been added successfully";
    }

    public String associateDoctor(
            AssociateDoctorDto associateDoctorDto)
            throws DoctorNotFound, CenterNotFound {

        Doctor doctor = doctorRepository
                .findById(associateDoctorDto.getDocId())
                .orElseThrow(() ->
                        new DoctorNotFound("Doctor not found"));

        VaccinationCenter center = vaccinationCenterRepository
                .findById(associateDoctorDto.getCenterId())
                .orElseThrow(() ->
                        new CenterNotFound("Vaccination center not found"));

        doctor.setVaccinationCenter(center);

        doctorRepository.save(doctor);

        return "Doctor has been associated to center successfully";
    }
}