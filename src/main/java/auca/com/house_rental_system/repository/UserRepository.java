package auca.com.house_rental_system.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import auca.com.house_rental_system.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<?> findByEmail(String email);

    @Query(value = """
            WITH RECURSIVE location_tree AS (
                SELECT id, code FROM locations WHERE name = :provinceName OR code = :provinceCode
                UNION ALL
                SELECT l.id, l.code FROM locations l
                INNER JOIN location_tree lt ON l.parent_id = lt.code
            )
            SELECT * FROM users u
            WHERE u.residence_location_id IN (SELECT id FROM location_tree)
            """, nativeQuery = true)
    List<User> findAllUsersInProvince(@Param("provinceName") String provinceName,
            @Param("provinceCode") String provinceCode);

}
