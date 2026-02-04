package com.example.TakeDose_Vaccination._Booking_System.Repository;

import com.example.TakeDose_Vaccination._Booking_System.Models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

}
