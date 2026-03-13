package auca.com.house_rental_system.controller;

import java.util.List;

import org.springframework.data.domain.*;

import org.springframework.web.bind.annotation.*;

import auca.com.house_rental_system.model.Location;
import auca.com.house_rental_system.service.LocationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/provinces")
    public Page<Location> getProvinces(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String dir) {

        Sort sort = dir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return locationService.getAllProvinces(pageable);
    }

    @GetMapping("/{parentId}/children")
    public Page<Location> getChildren(
            @PathVariable Long parentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String dir) {

        Sort sort = dir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return locationService.getChildren(parentId, pageable);
    }

    @PostMapping("/batch")
    public List<Location> saveLocationsBatch(@RequestBody List<Location> locations) {
        return locationService.saveAll(locations);
    }

}
