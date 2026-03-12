package auca.com.house_rental_system.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auca.com.house_rental_system.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Page<Location> findByType(String type, Pageable pageable);

    Page<Location> findByParentId(Long parentId, Pageable pageable);

    Location findByCode(String code);
}
