package com.example.TakeDose_Vaccination._Booking_System.Services;

import com.example.TakeDose_Vaccination._Booking_System.DTOs.UpdateEmailDto;
import com.example.TakeDose_Vaccination._Booking_System.Models.Dose;
import com.example.TakeDose_Vaccination._Booking_System.Models.User;
import com.example.TakeDose_Vaccination._Booking_System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {

        if (user.getEmailId() == null || user.getEmailId().isBlank()) {
            throw new RuntimeException("Email is required");
        }

        if (userRepository.findByEmailId(user.getEmailId()) != null) {
            throw new RuntimeException("Email already exists");
        }

        return userRepository.save(user);
    }

    public Date vaccinationDate(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Dose dose = user.getDose();

        if (dose == null) {
            throw new RuntimeException("Dose has not been given yet");
        }

        return dose.getVaccinationDate();
    }

    public String updateEmail(UpdateEmailDto updateEmailDto) {

        User user = userRepository.findById(updateEmailDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newEmail = updateEmailDto.getNewEmailId();

        if (newEmail == null || newEmail.isBlank()) {
            throw new RuntimeException("New email cannot be empty");
        }

        User existingUser = userRepository.findByEmailId(newEmail);

        if (existingUser != null &&
                !existingUser.getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Email already exists");
        }

        user.setEmailId(newEmail);
        userRepository.save(user);

        return "Email updated successfully";
    }

    public User findByEmailId(String emailId) {

        User user = userRepository.findByEmailId(emailId);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return user;
    }
}