package com.example.TakeDose_Vaccination._Booking_System.Services;

import com.example.TakeDose_Vaccination._Booking_System.DTOs.AppointmentReqDto;
import com.example.TakeDose_Vaccination._Booking_System.Exeptions.DoctorNotFound;
import com.example.TakeDose_Vaccination._Booking_System.Exeptions.UserNotFound;
import com.example.TakeDose_Vaccination._Booking_System.Models.Appointment;
import com.example.TakeDose_Vaccination._Booking_System.Models.Doctor;
import com.example.TakeDose_Vaccination._Booking_System.Models.User;
import com.example.TakeDose_Vaccination._Booking_System.Repository.AppointmentRepository;
import com.example.TakeDose_Vaccination._Booking_System.Repository.DoctorRepository;
import com.example.TakeDose_Vaccination._Booking_System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
   private  AppointmentRepository appointmentRepository;
    @Autowired
   private DoctorRepository doctorRepository;
    @Autowired
   private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;

    public String bookAppointment(AppointmentReqDto appointmentReqDto)throws DoctorNotFound, UserNotFound {
        Optional<Doctor> doctorOptional = doctorRepository.findById(appointmentReqDto.getDocId());
        if(!doctorOptional.isPresent()){
            throw new DoctorNotFound("Doctor Not Found");
        }

        Optional<User> userOptional = userRepository.findById(appointmentReqDto.getUserId());
        if(!userOptional.isPresent()){
            throw new UserNotFound("User Not Found");

        }

        Doctor doctor = doctorOptional.get();
        User user = userOptional.get();

        Appointment appointment = new  Appointment();
        appointment.setAppointmentDate(appointmentReqDto.getAppointmentDate());
        appointment.setAppointmetTime(appointmentReqDto.getAppointmentTime());

         appointment.setDoctor(doctor);
         appointment.setUser(user);

         appointmentRepository.save(appointment);
         doctor.getAppointmentList().add(appointment);
         user.getAppointmentList().add(appointment);

         doctorRepository.save(doctor);
         userRepository.save(user);

        String body = " Hi ! " + user.getName() + "\n" +
                "You have successfully booked an appointment on "
                + appointment.getAppointmentDate() + "at "
                + appointment.getAppointmetTime() + "\n" +
                "You doctor is " + doctor.getName() + "\n" +
                "Please reach at " + doctor.getVaccinationCenter().getAddress() + "\n"
                + "Mask is mandatory";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("abc@gmail.com");
        mailMessage.setTo(user.getEmailId());
        mailMessage.setSubject("Appointment Booked");
        mailMessage.setText(body);
        mailSender.send(mailMessage);
        return body;


    }


}
