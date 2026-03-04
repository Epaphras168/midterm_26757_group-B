package auca.com.house_rental_system.dto;

import auca.com.house_rental_system.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private User.Role role;
    private String residenceLocation; // Simplified for DTO

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.role = user.getRole();
        if (user.getResidenceLocation() != null) {
            // Assuming the Location model has a getName() method
            this.residenceLocation = user.getResidenceLocation().getName();
        }
    }
}