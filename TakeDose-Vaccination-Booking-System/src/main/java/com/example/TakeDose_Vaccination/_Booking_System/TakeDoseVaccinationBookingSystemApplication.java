package com.example.TakeDose_Vaccination._Booking_System;

import com.example.TakeDose_Vaccination._Booking_System.Models.Doctor;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class TakeDoseVaccinationBookingSystemApplication {

    public static void main(String[] args) {
         SpringApplication.run(TakeDoseVaccinationBookingSystemApplication.class, Arrays.toString(args));
        System.out.println("From tanda");


    }
}