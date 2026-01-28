package com.example.TakeDose_Vaccination._Booking_System.Controllers;

import com.example.TakeDose_Vaccination._Booking_System.DTOs.UpdateEmailDto;
import com.example.TakeDose_Vaccination._Booking_System.Models.User;
import com.example.TakeDose_Vaccination._Booking_System.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @GetMapping("/vaccinationDate")
    public Date vaccinationDate(@RequestParam("userId") Integer userId){
        return userService.vaccinationDate(userId);
    }

    @PutMapping("/updateEmail")
    public String updateEamil(@RequestBody UpdateEmailDto updateEmailDto ){
        return userService.updateEmail(updateEmailDto);
    }

    @GetMapping("/getUserById")
    public User findByEmailId(@RequestParam("emailId") String emailId){
        return userService.findByEmailId(emailId);
    }
}
