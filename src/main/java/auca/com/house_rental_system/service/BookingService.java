package auca.com.house_rental_system.service;

import auca.com.house_rental_system.model.Booking;
import auca.com.house_rental_system.model.Property;
import auca.com.house_rental_system.model.User;
import auca.com.house_rental_system.repository.BookingRepository;
import auca.com.house_rental_system.repository.PropertyRepository;
import auca.com.house_rental_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking createBooking(Long propertyId, Long tenantId, LocalDate startDate, LocalDate endDate) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));
        User tenant = userRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found"));

        if (!tenant.getRole().equals(User.Role.TENANT)) {
            throw new IllegalArgumentException("User must be a TENANT to create a booking");
        }

        // TODO: Could add logic here to check if the property is already booked for
        // these dates

        Booking booking = new Booking();
        booking.setProperty(property);
        booking.setTenant(tenant);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setStatus(Booking.BookingStatus.PENDING); // Default status

        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    public List<Booking> getBookingsByProperty(Long propertyId) {
        return bookingRepository.findByPropertyId(propertyId);
    }

    public List<Booking> getBookingsByTenant(Long tenantId) {
        return bookingRepository.findByTenantId(tenantId);
    }

    public Booking updateBookingStatus(Long bookingId, Booking.BookingStatus status) {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }
}
