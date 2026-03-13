package auca.com.house_rental_system.model;

import java.util.ArrayList;
import java.util.List;

import javax.management.relation.Role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "residence_location_id")
    private Location residenceLocation;

    @OneToMany(mappedBy = "landlord")
    @JsonIgnore
    private List<Property> properties = new ArrayList<>();

    @OneToMany(mappedBy = "tenant")
    @JsonIgnore
    private List<Booking> bookings = new ArrayList<>();

    public enum Role {
        LANDLORD, TENANT
    }
}
