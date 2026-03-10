package auca.com.house_rental_system.dto;

import auca.com.house_rental_system.model.Location;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationDto {
    private Long id;
    private String name;
    private String code;
    private String type;
    private String parentCode;
    private String parentName;

    public LocationDto(Location location) {
        this.id = location.getId();
        this.name = location.getName();
        this.code = location.getCode();
        this.type = location.getType();

        if (location.getParent() != null) {
            this.parentCode = location.getParent().getCode();
            this.parentName = location.getParent().getName();
        }
    }
}
