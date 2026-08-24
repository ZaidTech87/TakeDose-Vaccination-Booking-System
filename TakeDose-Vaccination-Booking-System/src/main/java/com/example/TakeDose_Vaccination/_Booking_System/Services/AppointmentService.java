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
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Transactional
    public String bookAppointment(
            AppointmentReqDto appointmentReqDto)
            throws DoctorNotFound, UserNotFound {

        if (appointmentReqDto.getAppointmentDate() == null) {
            throw new RuntimeException("Appointment date is required");
        }

        if (appointmentReqDto.getAppointmentTime() == null) {
            throw new RuntimeException("Appointment time is required");
        }

        if (appointmentReqDto.getAppointmentDate()
                .isBefore(LocalDate.now())) {

            throw new RuntimeException(
                    "Appointment date cannot be in the past"
            );
        }

        Optional<Doctor> doctorOptional =
                doctorRepository.findById(
                        appointmentReqDto.getDocId()
                );

        if (doctorOptional.isEmpty()) {
            throw new DoctorNotFound("Doctor not found");
        }

        Optional<User> userOptional =
                userRepository.findById(
                        appointmentReqDto.getUserId()
                );

        if (userOptional.isEmpty()) {
            throw new UserNotFound("User not found");
        }

        Doctor doctor = doctorOptional.get();
        User user = userOptional.get();

        if (doctor.getVaccinationCenter() == null) {
            throw new RuntimeException(
                    "Doctor is not associated with any vaccination center"
            );
        }

        boolean slotAlreadyBooked =
                appointmentRepository
                        .existsByDoctorDoctorIdAndAppointmentDateAndAppointmentTime(
                                doctor.getDoctorId(),
                                appointmentReqDto.getAppointmentDate(),
                                appointmentReqDto.getAppointmentTime()
                        );

        if (slotAlreadyBooked) {
            throw new RuntimeException(
                    "This appointment slot is already booked"
            );
        }

        Appointment appointment = new Appointment();

        appointment.setAppointmentDate(
                appointmentReqDto.getAppointmentDate()
        );

        appointment.setAppointmentTime(
                appointmentReqDto.getAppointmentTime()
        );

        appointment.setDoctor(doctor);
        appointment.setUser(user);

        appointmentRepository.save(appointment);

        String body =
                "Hi " + user.getName() + ",\n\n" +
                        "You have successfully booked an appointment.\n\n" +
                        "Date: " + appointment.getAppointmentDate() + "\n" +
                        "Time: " + appointment.getAppointmentTime() + "\n" +
                        "Doctor: " + doctor.getName() + "\n" +
                        "Address: " +
                        doctor.getVaccinationCenter().getAddress() + "\n\n" +
                        "Please wear a mask.";

        try {

            SimpleMailMessage mailMessage =
                    new SimpleMailMessage();

            mailMessage.setTo(user.getEmailId());
            mailMessage.setSubject("Appointment Booked");
            mailMessage.setText(body);

            mailSender.send(mailMessage);

        } catch (MailException e) {

            System.out.println(
                    "Email could not be sent: " + e.getMessage()
            );
        }

        return "Appointment booked successfully";
    }
}