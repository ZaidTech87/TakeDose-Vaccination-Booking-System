package com.example.TakeDose_Vaccination._Booking_System.Exeptions;

public class vaccinationAddressNotFound extends RuntimeException {
    public vaccinationAddressNotFound(String message)
    {
        super(message);
    }
}
