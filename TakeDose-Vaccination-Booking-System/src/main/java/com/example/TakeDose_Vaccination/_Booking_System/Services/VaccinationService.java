package com.example.TakeDose_Vaccination._Booking_System.Services;

import com.example.TakeDose_Vaccination._Booking_System.Exeptions.vaccinationAddressNotFound;
import com.example.TakeDose_Vaccination._Booking_System.Models.VaccinationCenter;
import com.example.TakeDose_Vaccination._Booking_System.Repository.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaccinationService {

    @Autowired
    private VaccinationCenterRepository vaccinationCenterRepository;

    public String addCenter(VaccinationCenter vaccinationCenter)
            throws vaccinationAddressNotFound {

        if (vaccinationCenter.getAddress() == null ||
                vaccinationCenter.getAddress().isBlank()) {

            throw new vaccinationAddressNotFound(
                    "Vaccination center address is required"
            );
        }

        if (vaccinationCenter.getOpeningTime() != null &&
                vaccinationCenter.getClosingTime() != null &&
                !vaccinationCenter.getOpeningTime()
                        .isBefore(vaccinationCenter.getClosingTime())) {

            throw new RuntimeException(
                    "Opening time must be before closing time"
            );
        }

        vaccinationCenterRepository.save(vaccinationCenter);

        return "Vaccination center added successfully at "
                + vaccinationCenter.getAddress();
    }
}