package com.example.TakeDose_Vaccination._Booking_System.Exeptions;

public class DoctorNotFound extends RuntimeException {
    public DoctorNotFound(String message) {

        super(message);
    }
}
