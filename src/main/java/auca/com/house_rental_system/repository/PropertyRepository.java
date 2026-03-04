package auca.com.house_rental_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.com.house_rental_system.model.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    
}
