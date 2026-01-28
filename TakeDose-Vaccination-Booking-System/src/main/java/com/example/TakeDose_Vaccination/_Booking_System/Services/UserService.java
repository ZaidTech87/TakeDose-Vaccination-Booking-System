package com.example.TakeDose_Vaccination._Booking_System.Services;

import com.example.TakeDose_Vaccination._Booking_System.DTOs.UpdateEmailDto;
import com.example.TakeDose_Vaccination._Booking_System.Models.Dose;
import com.example.TakeDose_Vaccination._Booking_System.Models.User;
import com.example.TakeDose_Vaccination._Booking_System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public User addUser(User user){
        System.out.println("This is user"+user);
        user= userRepository.save(user);
        return user;

    }
    public Date vaccinationDate(Integer userId){
        User user = userRepository.findById(userId).get();
        Dose dose = user.getDose();
        return dose.getVaccinationDate();
    }
    public String updateEmail(UpdateEmailDto updateEamilDto){
        int userId = updateEamilDto.getUserId();
        User user = userRepository.findById(userId).get();
        user.setEmailId(updateEamilDto.getNewEmailId());
        userRepository.save(user);
        return "old email id modied by new email "+updateEamilDto.getNewEmailId();
    }

    public User findByEmailId(String emailId){
        User user = userRepository.findByEmailId(emailId);
        return user;
    }


}
