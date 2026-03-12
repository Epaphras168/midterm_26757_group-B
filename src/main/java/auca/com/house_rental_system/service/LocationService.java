package auca.com.house_rental_system.service;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import auca.com.house_rental_system.model.Location;
import auca.com.house_rental_system.repository.LocationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public Page<Location> getAllProvinces(Pageable pageable) {
        return locationRepository.findByType("PROVINCE", pageable);
    }

    public Page<Location> getChildren(Long parentId, Pageable pageable) {
        return locationRepository.findByParentId(parentId, pageable);
    }

    // Recursively collect all descendant IDs
    public Set<Long> getAllDescendantIds(Location parent) {
        Set<Long> ids = new HashSet<>();
        ids.add(parent.getId());
        for (Location child : parent.getChildren()) {
            ids.addAll(getAllDescendantIds(child));
        }
        return ids;
    }

    @org.springframework.transaction.annotation.Transactional
    public List<Location> saveAll(List<Location> locations) {
        for (Location loc : locations) {
            if (loc.getParent() != null && loc.getParent().getCode() != null) {
                Location parentDB = locationRepository.findByCode(loc.getParent().getCode());
                loc.setParent(parentDB);
            }
        }
        return locationRepository.saveAll(locations);
    }

}
