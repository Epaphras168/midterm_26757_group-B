package auca.com.house_rental_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.com.house_rental_system.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByLevel(String level);
    List<Location> findByParentId(Long parentId);
}
