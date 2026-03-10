package auca.com.house_rental_system.dto;

import auca.com.house_rental_system.model.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDto {
    private Long id;
    private Integer rating;
    private String comment;
    private Long bookingId;

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();

        if (review.getBooking() != null) {
            this.bookingId = review.getBooking().getId();
        }
    }
}
