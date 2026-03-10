package auca.com.house_rental_system.dto;

import auca.com.house_rental_system.model.Amenity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AmenityDto {
    private Long id;
    private String name;

    public AmenityDto(Amenity amenity) {
        this.id = amenity.getId();
        this.name = amenity.getName();
    }
}
