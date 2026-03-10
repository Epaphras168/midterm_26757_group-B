package auca.com.house_rental_system.dto;

import auca.com.house_rental_system.model.Booking;
import auca.com.house_rental_system.model.Booking.BookingStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BookingDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private BookingStatus status;
    private Long tenantId;
    private String tenantName;
    private Long propertyId;
    private String propertyTitle;
    private Long reviewId;

    public BookingDto(Booking booking) {
        this.id = booking.getId();
        this.startDate = booking.getStartDate();
        this.endDate = booking.getEndDate();
        this.status = booking.getStatus();

        if (booking.getTenant() != null) {
            this.tenantId = booking.getTenant().getId();
            this.tenantName = booking.getTenant().getName();
        }

        if (booking.getProperty() != null) {
            this.propertyId = booking.getProperty().getId();
            this.propertyTitle = booking.getProperty().getTitle();
        }

        if (booking.getReview() != null) {
            this.reviewId = booking.getReview().getId();
        }
    }
}
