package auca.com.house_rental_system.service;

import java.util.*;

import org.springframework.stereotype.Service;

import auca.com.house_rental_system.model.Location;
import auca.com.house_rental_system.repository.LocationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<Location> getAllProvinces() {
        return locationRepository.findByType("PROVINCE");
    }

    public List<Location> getChildren(Long parentId) {
        return locationRepository.findByParentId(parentId);
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

}
