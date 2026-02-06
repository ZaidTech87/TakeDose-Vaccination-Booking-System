package com.example.TakeDose_Vaccination._Booking_System.DTOs;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
public class AppointmentReqDto {
    private Integer docId;
    private Integer userId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

}
