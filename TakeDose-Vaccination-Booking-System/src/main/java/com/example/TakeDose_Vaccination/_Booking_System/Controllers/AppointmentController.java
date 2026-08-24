package com.example.TakeDose_Vaccination._Booking_System.Controllers;

import com.example.TakeDose_Vaccination._Booking_System.DTOs.AppointmentReqDto;
import com.example.TakeDose_Vaccination._Booking_System.Services.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(
            AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookAppointment(
            @RequestBody AppointmentReqDto appointmentReqDto) {

        try {

            String result =
                    appointmentService.bookAppointment(
                            appointmentReqDto
                    );

            return ResponseEntity.ok(result);

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}