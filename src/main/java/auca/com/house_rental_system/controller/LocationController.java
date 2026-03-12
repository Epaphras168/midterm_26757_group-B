package auca.com.house_rental_system.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return locationService.getAllProvinces(pageable);
    }

    @GetMapping("/{parentId}/children")
    public Page<Location> getChildren(
            @PathVariable Long parentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return locationService.getChildren(parentId, pageable);
    }

    @PostMapping("/batch")
    public List<Location> saveLocationsBatch(@RequestBody List<Location> locations) {
        return locationService.saveAll(locations);
    }

}
