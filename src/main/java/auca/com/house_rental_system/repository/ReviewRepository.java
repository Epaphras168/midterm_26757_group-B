package auca.com.house_rental_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import auca.com.house_rental_system.model.Review;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByBookingId(Long bookingId);
}
