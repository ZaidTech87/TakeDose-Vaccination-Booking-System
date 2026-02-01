package com.example.TakeDose_Vaccination._Booking_System.Exeptions;

public class CenterNotFound extends RuntimeException {
    public CenterNotFound(String message) {

        super(message);
    }
}
