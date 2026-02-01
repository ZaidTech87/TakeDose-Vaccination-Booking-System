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

import java.util.Optional;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private VaccinationCenterRepository vaccinationCenterRepository;

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

    public String associateDoctor(AssociateDoctorDto associateDoctorDto) throws DoctorNotFound, CenterNotFound {
        Integer docId = associateDoctorDto.getDocId();
        Optional<Doctor> doctorOptional = doctorRepository.findById(docId);
        if(!doctorOptional.isPresent()){
              throw new DoctorNotFound("doctor is wrong");
        }

        Integer centerId = associateDoctorDto.getCenterId();
        Optional<VaccinationCenter> vaccinationCenterOptional = vaccinationCenterRepository.findById(centerId);
        if(!vaccinationCenterOptional.isPresent()) {
            throw new CenterNotFound("Center id incorrect entered");
        }
            Doctor doctor = doctorOptional.get();
            VaccinationCenter vaccinationCenter = vaccinationCenterOptional.get();
            doctor.setVaccinationCenter(vaccinationCenter);

            vaccinationCenter.getDoctorList().add(doctor);
            vaccinationCenterRepository.save(vaccinationCenter);
            return "Doctor has been associated to center";


    }
}
