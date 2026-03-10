package auca.com.house_rental_system.dto;

import auca.com.house_rental_system.model.Property;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PropertyDto {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Integer bedrooms;
    private Integer bathrooms;
    private Double area;
    private Long landlordId;
    private String landlordName;
    private String locationName;
    private Set<String> amenityNames;

    public PropertyDto(Property property) {
        this.id = property.getId();
        this.title = property.getTitle();
        this.description = property.getDescription();
        this.price = property.getPrice();
        this.bedrooms = property.getBedrooms();
        this.bathrooms = property.getBathrooms();
        this.area = property.getArea();

        if (property.getLandlord() != null) {
            this.landlordId = property.getLandlord().getId();
            this.landlordName = property.getLandlord().getName();
        }

        if (property.getLocation() != null) {
            this.locationName = property.getLocation().getName();
        }

        if (property.getAmenities() != null) {
            this.amenityNames = property.getAmenities().stream()
                    .map(amenity -> amenity.getName())
                    .collect(Collectors.toSet());
        }
    }
}
