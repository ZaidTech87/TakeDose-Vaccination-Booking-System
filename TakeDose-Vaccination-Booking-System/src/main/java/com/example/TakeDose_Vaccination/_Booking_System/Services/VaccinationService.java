package com.example.TakeDose_Vaccination._Booking_System.Services;

import com.example.TakeDose_Vaccination._Booking_System.Exeptions.vaccinationAddressNotFound;
import com.example.TakeDose_Vaccination._Booking_System.Models.VaccinationCenter;
import com.example.TakeDose_Vaccination._Booking_System.Repository.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaccinationService {
    @Autowired
    VaccinationCenterRepository vaccinationCenterRepository;

    public String addCenter(VaccinationCenter vaccinationCenter) throws vaccinationAddressNotFound
    {
        if(vaccinationCenter.getAddress()==null){
            throw new vaccinationAddressNotFound("Address not found");
        }
        vaccinationCenterRepository.save(vaccinationCenter);
        return "Vaccination center added at Locaton"+vaccinationCenter.getAddress();
    }

}
