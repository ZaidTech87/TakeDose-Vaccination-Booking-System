package com.example.TakeDose_Vaccination._Booking_System.Controllers;

import com.example.TakeDose_Vaccination._Booking_System.DTOs.AppointmentReqDto;
import com.example.TakeDose_Vaccination._Booking_System.Services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<String> bookAppointment(
            @RequestBody AppointmentReqDto dto) {

        try {

            String result =
                    appointmentService.bookAppointment(dto);

            return ResponseEntity.ok(result);

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // NEW
    @GetMapping("/available-slots")
    public ResponseEntity<?> getAvailableSlots(
            @RequestParam Integer doctorId,
            @RequestParam String date) {

        try {

            LocalDate appointmentDate =
                    LocalDate.parse(date);

            List<String> slots =
                    appointmentService.getAvailableSlots(
                            doctorId,
                            appointmentDate
                    );

            return ResponseEntity.ok(slots);

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}