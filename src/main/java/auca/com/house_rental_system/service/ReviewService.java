package auca.com.house_rental_system.service;

import auca.com.house_rental_system.model.Booking;
import auca.com.house_rental_system.model.Review;
import auca.com.house_rental_system.repository.BookingRepository;
import auca.com.house_rental_system.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Review createReview(Long bookingId, Integer rating, String comment) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (!booking.getStatus().equals(Booking.BookingStatus.CONFIRMED)) { // Example rule
            throw new IllegalArgumentException("Can only review confirmed bookings");
        }

        if (reviewRepository.findByBookingId(bookingId).isPresent()) {
            throw new IllegalArgumentException("Review already exists for this booking");
        }

        Review review = new Review();
        review.setBooking(booking);
        review.setRating(rating);
        review.setComment(comment);

        return reviewRepository.save(review);
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
    }

    public Review getReviewByBooking(Long bookingId) {
        return reviewRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found for this booking"));
    }
}
