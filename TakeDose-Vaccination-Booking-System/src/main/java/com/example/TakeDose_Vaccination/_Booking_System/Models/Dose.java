package com.example.TakeDose_Vaccination._Booking_System.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name="dose")
@Data
public class Dose {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String doseId;

    @CreationTimestamp
    private Date vaccinationDate;

    @OneToOne
    @JoinColumn
    private User user;

}
