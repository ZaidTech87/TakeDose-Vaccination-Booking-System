package com.example.TakeDose_Vaccination._Booking_System.Repository;

import com.example.TakeDose_Vaccination._Booking_System.Models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository
        extends JpaRepository<Doctor, Integer> {

    Doctor findByEmailId(String emailId);

    boolean existsByEmailId(String emailId);

    List<Doctor> findByVaccinationCenterId(Integer centerId);
}