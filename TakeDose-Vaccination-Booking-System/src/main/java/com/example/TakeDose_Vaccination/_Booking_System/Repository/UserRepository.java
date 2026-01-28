package com.example.TakeDose_Vaccination._Booking_System.Repository;
import com.example.TakeDose_Vaccination._Booking_System.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
 public User findByEmailId(String emailId);
}
