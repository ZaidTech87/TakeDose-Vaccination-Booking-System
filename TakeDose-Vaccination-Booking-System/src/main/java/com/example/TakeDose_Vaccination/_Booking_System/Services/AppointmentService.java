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
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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


    // =========================================================
    // BOOK APPOINTMENT
    // =========================================================

    @Transactional
    public String bookAppointment(
            AppointmentReqDto appointmentReqDto)
            throws DoctorNotFound, UserNotFound {

        // -----------------------------------------------------
        // 1. Validate request
        // -----------------------------------------------------

        if (appointmentReqDto == null) {
            throw new RuntimeException(
                    "Appointment details are required"
            );
        }

        if (appointmentReqDto.getAppointmentDate() == null) {
            throw new RuntimeException(
                    "Appointment date is required"
            );
        }

        if (appointmentReqDto.getAppointmentTime() == null) {
            throw new RuntimeException(
                    "Appointment time is required"
            );
        }

        if (appointmentReqDto.getDocId() == null) {
            throw new RuntimeException(
                    "Doctor ID is required"
            );
        }

        if (appointmentReqDto.getUserId() == null) {
            throw new RuntimeException(
                    "User ID is required"
            );
        }


        // -----------------------------------------------------
        // 2. Check past date
        // -----------------------------------------------------

        if (appointmentReqDto.getAppointmentDate()
                .isBefore(LocalDate.now())) {

            throw new RuntimeException(
                    "Past appointment date is not allowed"
            );
        }


        // -----------------------------------------------------
        // 3. Find doctor
        // -----------------------------------------------------

        Doctor doctor = doctorRepository
                .findById(appointmentReqDto.getDocId())
                .orElseThrow(() ->
                        new DoctorNotFound(
                                "Doctor not found"
                        )
                );


        // -----------------------------------------------------
        // 4. Find user
        // -----------------------------------------------------

        User user = userRepository
                .findById(appointmentReqDto.getUserId())
                .orElseThrow(() ->
                        new UserNotFound(
                                "User not found"
                        )
                );


        // -----------------------------------------------------
        // 5. Check doctor vaccination center
        // -----------------------------------------------------

        if (doctor.getVaccinationCenter() == null) {

            throw new RuntimeException(
                    "Doctor is not associated with any vaccination center"
            );
        }


        // -----------------------------------------------------
        // 6. Check opening and closing time
        // -----------------------------------------------------

        LocalTime openingTime =
                doctor.getVaccinationCenter().getOpeningTime();

        LocalTime closingTime =
                doctor.getVaccinationCenter().getClosingTime();

        if (openingTime == null || closingTime == null) {

            throw new RuntimeException(
                    "Vaccination center working hours are not configured"
            );
        }


        // -----------------------------------------------------
        // 7. Check appointment time is within center hours
        // -----------------------------------------------------

        LocalTime appointmentTime =
                appointmentReqDto.getAppointmentTime();

        if (appointmentTime.isBefore(openingTime)
                || !appointmentTime.isBefore(closingTime)) {

            throw new RuntimeException(
                    "Appointment time is outside vaccination center working hours"
            );
        }


        // -----------------------------------------------------
        // 8. Check 30-minute slot
        // -----------------------------------------------------

        if (appointmentTime.getMinute() != 0
                && appointmentTime.getMinute() != 30) {

            throw new RuntimeException(
                    "Appointment time must be a 30-minute slot"
            );
        }


        // -----------------------------------------------------
        // 9. If booking is today, past time is not allowed
        // -----------------------------------------------------

        if (appointmentReqDto.getAppointmentDate()
                .equals(LocalDate.now())
                && appointmentTime.isBefore(LocalTime.now())) {

            throw new RuntimeException(
                    "Past appointment time is not allowed"
            );
        }


        // -----------------------------------------------------
        // 10. Check duplicate booking
        // -----------------------------------------------------

        boolean alreadyBooked =
                appointmentRepository
                        .existsByDoctorDoctorIdAndAppointmentDateAndAppointmentTime(
                                doctor.getDoctorId(),
                                appointmentReqDto.getAppointmentDate(),
                                appointmentTime
                        );

        if (alreadyBooked) {

            throw new RuntimeException(
                    "This appointment slot is already booked"
            );
        }


        // -----------------------------------------------------
        // 11. Create appointment
        // -----------------------------------------------------

        Appointment appointment =
                new Appointment();

        appointment.setAppointmentDate(
                appointmentReqDto.getAppointmentDate()
        );

        appointment.setAppointmentTime(
                appointmentTime
        );

        appointment.setDoctor(doctor);

        appointment.setUser(user);


        // -----------------------------------------------------
        // 12. Save appointment
        // -----------------------------------------------------

        appointmentRepository.save(appointment);


        // -----------------------------------------------------
        // 13. Send confirmation email
        // -----------------------------------------------------

        String body =
                "Hi " + user.getName() + ",\n\n" +

                        "Your vaccination appointment has been "
                        + "booked successfully.\n\n" +

                        "Appointment Details:\n" +
                        "--------------------\n" +

                        "Doctor: "
                        + doctor.getName() + "\n" +

                        "Date: "
                        + appointment.getAppointmentDate() + "\n" +

                        "Time: "
                        + appointment.getAppointmentTime() + "\n" +

                        "Vaccination Center: "
                        + doctor.getVaccinationCenter().getCentreName()
                        + "\n" +

                        "Address: "
                        + doctor.getVaccinationCenter().getAddress()
                        + "\n\n" +

                        "Please arrive a few minutes before "
                        + "your appointment.\n\n" +

                        "Regards,\n"
                        + "TakeDose Team";


        try {

            SimpleMailMessage mailMessage =
                    new SimpleMailMessage();

            mailMessage.setTo(
                    user.getEmailId()
            );

            mailMessage.setSubject(
                    "TakeDose - Appointment Confirmation"
            );

            mailMessage.setText(body);

            mailSender.send(mailMessage);

        } catch (MailException e) {

            // Email failure should not cancel
            // the successfully created appointment.

            System.out.println(
                    "Email could not be sent: "
                            + e.getMessage()
            );
        }


        return "Appointment booked successfully";
    }


    // =========================================================
    // GET AVAILABLE SLOTS
    // =========================================================

    public List<String> getAvailableSlots(
            Integer doctorId,
            LocalDate date) {

        // -----------------------------------------------------
        // 1. Validate input
        // -----------------------------------------------------

        if (doctorId == null) {

            throw new RuntimeException(
                    "Doctor ID is required"
            );
        }

        if (date == null) {

            throw new RuntimeException(
                    "Appointment date is required"
            );
        }


        // -----------------------------------------------------
        // 2. Check past date
        // -----------------------------------------------------

        if (date.isBefore(LocalDate.now())) {

            throw new RuntimeException(
                    "Past date is not allowed"
            );
        }


        // -----------------------------------------------------
        // 3. Find doctor
        // -----------------------------------------------------

        Doctor doctor = doctorRepository
                .findById(doctorId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found"
                        )
                );


        // -----------------------------------------------------
        // 4. Check vaccination center
        // -----------------------------------------------------

        if (doctor.getVaccinationCenter() == null) {

            throw new RuntimeException(
                    "Doctor is not associated with a vaccination center"
            );
        }


        // -----------------------------------------------------
        // 5. Get center working hours
        // -----------------------------------------------------

        LocalTime openingTime =
                doctor.getVaccinationCenter()
                        .getOpeningTime();

        LocalTime closingTime =
                doctor.getVaccinationCenter()
                        .getClosingTime();

        if (openingTime == null
                || closingTime == null) {

            throw new RuntimeException(
                    "Vaccination center working hours are not configured"
            );
        }


        // -----------------------------------------------------
        // 6. Get booked appointments
        // -----------------------------------------------------

        List<Appointment> bookedAppointments =
                appointmentRepository
                        .findByDoctorDoctorIdAndAppointmentDate(
                                doctorId,
                                date
                        );


        // -----------------------------------------------------
        // 7. Extract booked times
        // -----------------------------------------------------

        List<LocalTime> bookedTimes =
                bookedAppointments.stream()
                        .map(Appointment::getAppointmentTime)
                        .toList();


        // -----------------------------------------------------
        // 8. Generate available slots
        // -----------------------------------------------------

        List<String> availableSlots =
                new ArrayList<>();

        LocalTime current =
                openingTime;


        while (current.isBefore(closingTime)) {

            boolean alreadyBooked =
                    bookedTimes.contains(current);

            boolean isPastTime =
                    date.equals(LocalDate.now())
                            && !current.isAfter(LocalTime.now());


            if (!alreadyBooked && !isPastTime) {

                availableSlots.add(
                        current.toString()
                );
            }


            // Generate 30-minute slots
            current = current.plusMinutes(30);
        }


        return availableSlots;
    }
}