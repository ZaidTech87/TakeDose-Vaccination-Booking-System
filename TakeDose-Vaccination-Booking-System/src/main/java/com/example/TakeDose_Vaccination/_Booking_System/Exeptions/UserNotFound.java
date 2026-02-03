package com.example.TakeDose_Vaccination._Booking_System.Exeptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String message)
    {
        super(message);
    }
}
