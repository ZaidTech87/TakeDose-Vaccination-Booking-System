package com.example.TakeDose_Vaccination._Booking_System.Services;

import com.example.TakeDose_Vaccination._Booking_System.Models.Dose;
import com.example.TakeDose_Vaccination._Booking_System.Models.User;
import com.example.TakeDose_Vaccination._Booking_System.Repository.DoseRepository;
import com.example.TakeDose_Vaccination._Booking_System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoseService {

    @Autowired
    private DoseRepository doseRepository;

    @Autowired
    private UserRepository userRepository;

    public String giveDose(String doseId, Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getDose() != null) {
            return "Dose already given to this user";
        }

        if (doseRepository.existsByDoseId(doseId)) {
            return "Dose ID already exists";
        }

        Dose dose = new Dose();
        dose.setDoseId(doseId);
        dose.setUser(user);

        user.setDose(dose);

        doseRepository.save(dose);

        return "Dose given successfully";
    }
}